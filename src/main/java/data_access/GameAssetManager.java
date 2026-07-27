package data_access;

import application.game_registry.InteractableRegistry;
import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintDataAccessInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.Hint.Hint;
import domain.entities.Hint.HintFactory;
import domain.entities.Interactable.Interactable;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.Room.Position;
import domain.entities.Room.Room;
import domain.entities.Room.RoomFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages game flow as the centralized data initialization engine and in-memory vault
 * for all static, read-only game assets.
 */
public class GameAssetManager implements RoomRegistry, ItemRegistry, InteractableRegistry,
        GetHintDataAccessInterface, BrowseRoomsDataAccessInterface {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ItemFactory itemFactory;
    private final InteractableFactory interactableFactory;
    private final RoomFactory roomFactory;
    private final HintFactory hintFactory;

    // Master in-memory static template lookups
    private final Map<String, Room> masterRooms = new HashMap<>();
    private final Map<String, Item> masterItems = new HashMap<>();
    private final Map<String, Interactable> masterInteractables = new HashMap<>();
    private final Map<String, Hint> masterHints = new HashMap<>();
    private final Map<String,List<Room>> masterModes = new HashMap<>();

    public GameAssetManager(ItemFactory itemFactory,
                            InteractableFactory interactableFactory,
                            RoomFactory roomFactory,
                            HintFactory hintFactory) {

        this.itemFactory = itemFactory;
        this.interactableFactory = interactableFactory;
        this.roomFactory = roomFactory;
        this.hintFactory = hintFactory;
        try {
            loadItems();
            loadInteractables(); // Must run before rooms to populate dependencies
            loadHints();
            loadRooms();         // Instantiates rooms and links child interactables
            loadModes();         // Must run after rooms to populate Room lists for each mode
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
                        data.imagePath,
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

                // Create a list of Interactable objects to add to Room object
                List<Interactable> interactableList = new ArrayList<>();
                for (String interactableId : data.interactables) {
                    Interactable interactable = getInteractableByID(interactableId);
                    interactableList.add(interactable);
                }

                // Create a list of Item objects to add to Room object
                List<Item> itemList = new ArrayList<>();
                for (String itemId : data.items) {
                    Item item = getItemByID(itemId);
                    itemList.add(item);
                }

                // Create a list of Hint objects to add to Room object
                List<Hint> hintList = new ArrayList<>();
                for (String objectId : data.hints) {
                    Hint hint = getHintForObjectID(objectId);
                    hintList.add(hint);
                }

                Room room = roomFactory.createRoom(roomId, data.description, data.imagePath,
                        interactableList, itemList, hintList);

                for (Hint hint : room.getHints()) {
                    List<Integer> positionList = hint.getHintObjectPosition();
                    if (positionList != null && positionList.size() >= 2) {
                        Position position = new Position(positionList.get(0), positionList.get(1));
                        room.setPosition(hint.getObjectID(), position);
                    }
                }

                // TODO: add positions of interactables and items to room
                masterRooms.put(roomId, room);
            }
        }
    }

    private void loadModes() throws IOException {
        Path path = Paths.get("data/modes.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        Map<String, List<String>> result = gson.fromJson(json, type);

        if (result != null){
            for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                String mode = entry.getKey();
                List<String> roomIDs = entry.getValue();
                // For each mode, create a new list to populate with Room objects
                List<Room> roomList = new ArrayList<>();

                // For each roomID saved in database, get a Room object with that ID and add it to the Room object list.
                for (String roomID : roomIDs) {
                    Room roomObject = getRoomByID(roomID);
                    roomList.add(roomObject);
                }

                // Put the mode and its Rooms to masterModes variable
                masterModes.put(mode, roomList);
            }
        }

    }

    private void loadHints() throws IOException {
        Path path = Paths.get("data/hints.json");
        if (!Files.exists(path)) return;

        String json = Files.readString(path);
        Type type = new TypeToken<Map<String, JsonHintData>>() {}.getType();
        Map<String, JsonHintData> rawData = gson.fromJson(json, type);

        if (rawData != null) {
            for (Map.Entry<String, JsonHintData> entry : rawData.entrySet()) {
                String hintId = entry.getKey();
                JsonHintData data = entry.getValue();

                Hint hintObject = hintFactory.createHint(hintId, data.imagePath, data.hintMessages, data.position);

                masterHints.put(hintId, hintObject);
            }
        }
    }


    // =========================================================================
    // Registry implementations (DBs only saves text IDs)
    // =========================================================================

    @Override
    public Room getRoomByID(String id) {
        return masterRooms.get(id);
    }

    @Override
    public Item getItemByID(String id) {
        return masterItems.get(id);
    }

    @Override
    public Interactable getInteractableByID(String ID) {
        return masterInteractables.get(ID);
    }


    // =========================================================================
    // GetHintDataAccessInterface implementation
    // =========================================================================
    @Override
    public Hint getHintForObjectID(String objectID) {
        return masterHints.get(objectID);
    }

    @Override
    public Boolean existByObjectID(String objectID) {
        return masterHints.containsKey(objectID);
    }


    // =========================================================================
    // BrowseRoomsDataAccessInterface implementation
    // =========================================================================
    @Override
    public List<Room> getRoomsForQuickMode() {
        return masterModes.get("QuickMode");
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
        String imagePath;
        List<String> interactables;
        List<String> items;
        List<String> hints;
    }

    private static class JsonInteractableData {
        String imagePath;
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
    }

    private static class JsonHintData {
        String imagePath;
        List<String> hintMessages;
        List<Integer> position;
    }
}
