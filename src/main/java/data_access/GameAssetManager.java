package data_access;

import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.Interactable.Interactable;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.Room.Room;
import domain.entities.Room.RoomFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages game flow as the centralized data initialization engine and in-memory vault
 * for all static, read-only game assets.
 */
public class GameAssetManager implements RoomRegistry, ItemRegistry {
    // TODO: make the manager implement GetHintDataAccessInterface
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ItemFactory itemFactory;
    private final InteractableFactory interactableFactory;
    private final RoomFactory roomFactory;

    // Master in-memory static template lookups
    private final Map<String, Room> masterRooms = new HashMap<>();
    private final Map<String, Item> masterItems = new HashMap<>();
    private final Map<String, Interactable> masterInteractables = new HashMap<>();
    private Map<String, List<String>> masterHints = new HashMap<>();

    public GameAssetManager(ItemFactory itemFactory,
                            InteractableFactory interactableFactory,
                            RoomFactory roomFactory) {

        this.itemFactory = itemFactory;
        this.interactableFactory = interactableFactory;
        this.roomFactory = roomFactory;
        try {
            loadItems();
            loadInteractables(); // Must run before rooms to populate dependencies
            loadRooms();         // Instantiates rooms and links child interactables
            loadHints();
        } catch (IOException e) {
            throw new RuntimeException("Static game assets initialization crashed: ", e);
        }
    }

    private void loadItems() throws IOException {
        Path path = Paths.get("data/items.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, JsonItemData>>() {}.getType();
        Map<String, JsonItemData> rawData = gson.fromJson(json, type);

        if (rawData != null) {
            for (Map.Entry<String, JsonItemData> entry : rawData.entrySet()) {
                String stringId = entry.getKey();
                JsonItemData data = entry.getValue();

                // Pass the string ID directly to restoreItem
                // TODO: add the imagePath parameter in itemFactory for image loading
                // TODO: change restoreItem UUID type to String type for itemId.
                Item item = itemFactory.restoreItem(stringId, data.name, data.description, data.craftable, data.imagePath);
                masterItems.put(stringId, item);
            }
        }
    }

    private void loadInteractables() throws IOException {
        Path path = Paths.get("data/interactables.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, JsonInteractableData>>() {}.getType();
        Map<String, JsonInteractableData> rawData = gson.fromJson(json, type);

        if (rawData != null) {
            for (Map.Entry<String, JsonInteractableData> entry : rawData.entrySet()) {
                String id = entry.getKey();
                JsonInteractableData data = entry.getValue();

                // Construct using the factory contract
                Interactable interactable = interactableFactory.create(
                        id,
                        data.defaultName,
                        data.defaultDescription,
                        data.defaultSprite,
                        data.interactedName,
                        data.interactedDescription,
                        data.interactedSprite,
                        data.isConsumed,
                        data.consumesItem,
                        data.needsItem,
                        data.requiredItemId,
                        data.rewardItemId,
                        data.linkedPuzzleId,
                        data.unlockedRoomId,
                        data.successMessage
                        // TODO: add imagePath to interactableFactory method
                );
                masterInteractables.put(id, interactable);
            }
        }
    }

    private void loadRooms() throws IOException {
        Path path = Paths.get("data/rooms.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, JsonRoomData>>() {}.getType();
        Map<String, JsonRoomData> rawData = gson.fromJson(json, type);

        if (rawData != null) {
            for (Map.Entry<String, JsonRoomData> entry : rawData.entrySet()) {
                String roomId = entry.getKey();
                JsonRoomData data = entry.getValue();

                // TODO: may need to change (may need a restoreRoom method from Room.
                //  Sample: restoreRoom(String roomName, List<String> interactableIDs, List<String> itemIDs, String imagePath)
                Room room = roomFactory.createRoom(roomId);

                // Nest instantiated objects into rooms
                if (data.interactables != null) {
                    for (String interactableId : data.interactables) {
                        Interactable interactable = masterInteractables.get(interactableId);
                        if (interactable != null) {
                            room.addInteractable(interactable);
                        }
                    }
                }

                // Sample code with restoreRoom:
                // Room room = roomFactory.restoreRoom(roomID,
                //                                     data.description,
                //                                     data.interactables,
                //                                     data.items,
                //                                     data.imagePath)
                masterRooms.put(roomId, room);
            }
        }
    }

    private void loadHints() throws IOException {
        Path path = Paths.get("data/hints.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        Map<String, List<String>> result = gson.fromJson(json, type);

        if (result != null) {
            this.masterHints = result;
        }
    }

    // Used to restore Room objects when logging a user in. User DB only saves roomIDs.
    @Override
    public Room getRoomByID(String id) {
        return masterRooms.get(id);
    }

    // Used to restore Item objects when logging a user in. User DB only saves itemIDs.
    @Override
    public Item getItemByID(String id) {
        return masterItems.get(id);
    }

    // =========================================================================
    // Private Schema Mapping DTO Classes (Kept Isolated from Business Rules)
    // =========================================================================
    private static class JsonItemData {
        String name;
        String description;
        boolean craftable = false;
        String imagePath;
    }

    private static class JsonRoomData {
        String description;
        List<String> interactables;
        List<String> items;
        String imagePath;
    }

    // TODO: need imagePath in InteractableFactory
    private static class JsonInteractableData {
        String defaultName;
        String defaultDescription;
        String defaultSprite;
        String interactedName;
        String interactedDescription;
        String interactedSprite;
        boolean isConsumed;
        boolean consumesItem;
        boolean needsItem;
        String requiredItemId;
        String rewardItemId;
        String linkedPuzzleId;
        String unlockedRoomId;
        String successMessage;
        String imagePath;
    }
}
