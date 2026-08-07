package data_access;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.game_registry.InteractableRegistry;
import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.Hint.GetHint.GetHintDataAccessInterface;
import application.use_cases.Interactable.Interact.InteractDataAccessInterface;
import application.use_cases.Interactable.Zoom.ZoomDataAccessInterface;
import application.use_cases.Puzzle.EnterExit.EnterExitDataAccessInterface;
import application.use_cases.Puzzle.Solve.SolveDataAccessInterface;
import application.use_cases.game_play.QuickPlay.BrowseRooms.BrowseRoomsDataAccessInterface;
import application.use_cases.game_play.TutorialAndStoryModeStartUp.StartUpDataAccessInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.Hint.Hint;
import domain.entities.Hint.HintFactory;
import domain.entities.Interactable.Interactable;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Puzzle.PuzzleFactory;
import domain.entities.Room.Position;
import domain.entities.Room.Room;
import domain.entities.Room.RoomFactory;

/**
 * Manages game flow as the centralized data initialization engine and in-memory vault
 * for all static, read-only game assets.
 */
public class GameAssetManager implements
        RoomRegistry, ItemRegistry, InteractableRegistry,
        GetHintDataAccessInterface,
        BrowseRoomsDataAccessInterface, StartUpDataAccessInterface,
        InteractDataAccessInterface, EnterExitDataAccessInterface, SolveDataAccessInterface, ZoomDataAccessInterface {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ItemFactory itemFactory;
    private final InteractableFactory interactableFactory;
    private final RoomFactory roomFactory;
    private final HintFactory hintFactory;
    private final PuzzleFactory puzzleFactory;
    private final PuzzleGenerator puzzleGenerator;

    // Master in-memory static template lookups
    private final Map<String, Room> masterRooms = new HashMap<>();
    private final Map<String, Item> masterItems = new HashMap<>();
    private final Map<String, Interactable> masterInteractables = new HashMap<>();
    private final Map<String, Hint> masterHints = new HashMap<>();
    private final Map<String, List<Room>> masterModes = new HashMap<>();
    private final Map<String, Puzzle> masterPuzzles = new HashMap<>();

    public GameAssetManager(ItemFactory itemFactory,
                            InteractableFactory interactableFactory,
                            RoomFactory roomFactory,
                            HintFactory hintFactory,
                            PuzzleGenerator puzzleGenerator,
                            PuzzleFactory puzzleFactory) {

        this.itemFactory = itemFactory;
        this.interactableFactory = interactableFactory;
        this.roomFactory = roomFactory;
        this.hintFactory = hintFactory;
        this.puzzleGenerator = puzzleGenerator;
        this.puzzleFactory = puzzleFactory;
        try {
            loadItems();
            loadPuzzles();
            // Must run before rooms to populate dependencies:
            loadInteractables();
            loadHints();
            // Instantiates rooms and links child interactables:
            loadRooms();
            // Must run after rooms to populate Room lists for each mode:
            loadModes();
        }
        catch (IOException exception) {
            throw new RuntimeException("Static game assets initialization crashed: ", exception);
        }
    }

    private void loadItems() throws IOException {
        // Read from the classpath resources
        try (InputStream stream = getClass().getResourceAsStream("/data/items.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, JsonItemData>>() {
                    }.getType();
                    final Map<String, JsonItemData> rawData = gson.fromJson(reader, type);

                    if (rawData != null) {
                        for (Map.Entry<String, JsonItemData> entry : rawData.entrySet()) {
                            final String stringId = entry.getKey();
                            final JsonItemData data = entry.getValue();

                            // Pass the string ID directly to restoreItem
                            final Item item = itemFactory.restoreItem(stringId,
                                    data.getName(), data.getDescription(), data.isCraftable(), data.getImagePath());
                            masterItems.put(stringId, item);
                        }
                    }
                }
            }
        }
    }

    private void loadPuzzles() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/data/puzzles.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, JsonPuzzleData>>() {
                    }.getType();
                    final Map<String, JsonPuzzleData> rawData = gson.fromJson(reader, type);

                    if (rawData != null) {
                        for (Map.Entry<String, JsonPuzzleData> entry : rawData.entrySet()) {
                            final String puzzleId = entry.getKey();
                            final JsonPuzzleData data = entry.getValue();

                            if ("Anagram".equalsIgnoreCase(data.getPuzzleType())) {
                                final List<String> answers = new ArrayList<>();
                                answers.add(data.getAnswer());
                                final Puzzle puzzle = puzzleFactory.createAnagram(puzzleId,
                                        data.getScrambled(),
                                        answers,
                                        data.getSuccessMessage(),
                                        data.getRewardItemId(),
                                        data.getUnlockedRoomId());
                                masterPuzzles.put(puzzleId, puzzle);
                            }
                            else {
                                final Puzzle puzzle = puzzleGenerator.generateCryptogramPuzzle(puzzleId,
                                        data.getCipherKeyId(),
                                        data.getSuccessMessage(),
                                        data.getRewardItemId(),
                                        data.getUnlockedRoomId());
                                masterPuzzles.put(puzzleId, puzzle);
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadInteractables() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/data/interactables.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, JsonInteractableData>>() {
                    }.getType();
                    final Map<String, JsonInteractableData> rawData = gson.fromJson(reader, type);

                    if (rawData != null) {
                        for (Map.Entry<String, JsonInteractableData> entry : rawData.entrySet()) {
                            final String id = entry.getKey();
                            final JsonInteractableData data = entry.getValue();

                            // Construct using the factory contract
                            final Interactable interactable = interactableFactory.create(
                                    id,
                                    data.getDefaultName(),
                                    data.getDefaultDescription(),
                                    data.getDefaultSprite(),
                                    data.getInteractedName(),
                                    data.getInteractedDescription(),
                                    data.getInteractedSprite(),
                                    data.isConsumed(),
                                    data.isConsumesItem(),
                                    data.isNeedsItem(),
                                    data.getRequiredItemId(),
                                    data.getRewardItemId(),
                                    data.getLinkedPuzzleId(),
                                    data.getUnlockedRoomId(),
                                    data.getSuccessMessage()
                            );
                            masterInteractables.put(id, interactable);
                        }
                    }
                }
            }
        }
    }

    private void loadRooms() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/data/rooms.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, JsonRoomData>>() {
                    }.getType();
                    final Map<String, JsonRoomData> rawData = gson.fromJson(reader, type);

                    if (rawData != null) {
                        for (Map.Entry<String, JsonRoomData> entry : rawData.entrySet()) {
                            final String roomId = entry.getKey();
                            final JsonRoomData data = entry.getValue();

                            // Create a list of Interactable objects to add to Room object
                            final List<Interactable> interactableList = new ArrayList<>();
                            for (String interactableId : data.getInteractables()) {
                                final Interactable interactable = getInteractableById(interactableId);
                                interactableList.add(interactable);
                            }

                            // Create a list of Item objects to add to Room object
                            final List<Item> itemList = new ArrayList<>();
                            for (String itemId : data.getItems()) {
                                final Item item = getItemById(itemId);
                                itemList.add(item);
                            }

                            // Create a list of Hint objects to add to Room object
                            final List<Hint> hintList = new ArrayList<>();
                            for (String objectId : data.getHints()) {
                                final Hint hint = getHintForObjectID(objectId);
                                hintList.add(hint);
                            }

                            final Map<String, Position> posMap = getPositionMap(data);

                            final Room room = roomFactory.createRoom(roomId, data.getDescription(), data.getImagePath(),
                                    interactableList, itemList, hintList, posMap);

                            masterRooms.put(roomId, room);
                        }
                    }
                }
            }
        }
    }

    private static Map<String, Position> getPositionMap(JsonRoomData data) {
        final Map<String, Position> posMap = new HashMap<>();
        if (data.getPositions() != null) {
            for (Map.Entry<String, List<Double>> positionEntry : data.getPositions().entrySet()) {
                final String objectID = positionEntry.getKey();
                final List<Double> coordinates = positionEntry.getValue();

                if (coordinates != null && coordinates.size() >= 2) {
                    final Position pos = new Position(coordinates.get(0), coordinates.get(1));
                    posMap.put(objectID, pos);
                }
            }
        }
        return posMap;
    }

    private void loadModes() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/data/modes.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, List<String>>>() {
                    }.getType();
                    final Map<String, List<String>> result = gson.fromJson(reader, type);

                    if (result != null) {
                        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                            final String mode = entry.getKey();
                            final List<String> roomIds = entry.getValue();
                            // For each mode, create a new list to populate with Room objects
                            final List<Room> roomList = new ArrayList<>();

                            // For each roomID saved in database,
                            // get a Room object with that ID and add it to the Room object list.
                            for (String roomID : roomIds) {
                                final Room roomObject = getRoomById(roomID);
                                roomList.add(roomObject);
                            }

                            // Put the mode and its Rooms to masterModes variable
                            masterModes.put(mode, roomList);
                        }
                    }
                }
            }
        }
    }

    private void loadHints() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/data/hints.json")) {
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    final Type type = new TypeToken<Map<String, JsonHintData>>() {
                    }.getType();
                    final Map<String, JsonHintData> rawData = gson.fromJson(reader, type);

                    if (rawData != null) {
                        for (Map.Entry<String, JsonHintData> entry : rawData.entrySet()) {
                            final String hintId = entry.getKey();
                            final JsonHintData data = entry.getValue();

                            final Hint hintObject = hintFactory.createHint(hintId,
                                    data.getImagePath(), data.getHintMessages());

                            masterHints.put(hintId, hintObject);
                        }
                    }
                }
            }
        }
    }

    // =========================================================================
    // Registry implementations (DBs only saves text IDs)
    // =========================================================================

    @Override
    public Room getRoomById(String id) {
        return masterRooms.get(id);
    }

    @Override
    public Item getItemById(String id) {
        return masterItems.get(id);
    }

    @Override
    public Interactable getInteractableById(String ID) {
        return masterInteractables.get(ID);
    }

    @Override
    public Puzzle getPuzzleById(String puzzleId) {
        return masterPuzzles.get(puzzleId);
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
    // StartUpDataAccessInterface implementation
    // =========================================================================

    @Override
    public Room findStartingRoomForTut() {
        return masterModes.get("TutorialMode").getFirst();
    }

    @Override
    public Room findStartingRoomForStory() {
        return masterModes.get("StoryMode").getFirst();
    }

    // =========================================================================
    // Private Schema Mapping DTO Classes (Kept Isolated from Business Rules)
    // =========================================================================
    private static final class JsonItemData {
        private String name;
        private String description;
        private boolean craftable;
        private String imagePath;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCraftable() {
            return craftable;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    private static final class JsonRoomData {
        private String description;
        private String imagePath;
        private List<String> interactables;
        private List<String> items;
        private List<String> hints;
        private Map<String, List<Double>> positions;

        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public List<String> getInteractables() {
            return interactables;
        }

        public List<String> getItems() {
            return items;
        }

        public List<String> getHints() {
            return hints;
        }

        public Map<String, List<Double>> getPositions() {
            return positions;
        }
    }

    private static final class JsonInteractableData {
        private String defaultName;
        private String defaultDescription;
        private String defaultSprite;
        private String interactedName;
        private String interactedDescription;
        private String interactedSprite;
        private boolean isConsumed;
        private boolean consumesItem;
        private boolean needsItem;
        private String requiredItemId;
        private String rewardItemId;
        private String linkedPuzzleId;
        private String unlockedRoomId;
        private String successMessage;

        public String getDefaultName() {
            return defaultName;
        }

        public String getDefaultDescription() {
            return defaultDescription;
        }

        public String getDefaultSprite() {
            return defaultSprite;
        }

        public String getInteractedName() {
            return interactedName;
        }

        public String getInteractedDescription() {
            return interactedDescription;
        }

        public String getRewardItemId() {
            return rewardItemId;
        }

        public boolean isConsumed() {
            return isConsumed;
        }

        public boolean isConsumesItem() {
            return consumesItem;
        }

        public boolean isNeedsItem() {
            return needsItem;
        }

        public String getUnlockedRoomId() {
            return unlockedRoomId;
        }

        public String getInteractedSprite() {
            return interactedSprite;
        }

        public String getRequiredItemId() {
            return requiredItemId;
        }

        public String getLinkedPuzzleId() {
            return linkedPuzzleId;
        }

        public String getSuccessMessage() {
            return successMessage;
        }
    }

    private static final class JsonHintData {
        private String imagePath;
        private List<String> hintMessages;

        public String getImagePath() {
            return imagePath;
        }

        public List<String> getHintMessages() {
            return hintMessages;
        }
    }

    private static final class JsonPuzzleData {
        private String puzzleType;
        private String scrambled;
        private String answer;
        private String hint;
        private String successMessage;
        private String rewardItemId;
        private String unlockedRoomId;
        private String cipherKeyId;

        public String getSuccessMessage() {
            return successMessage;
        }

        public String getAnswer() {
            return answer;
        }

        public String getPuzzleType() {
            return puzzleType;
        }

        public String getScrambled() {
            return scrambled;
        }

        public String getRewardItemId() {
            return rewardItemId;
        }

        public String getHint() {
            return hint;
        }

        public String getCipherKeyId() {
            return cipherKeyId;
        }

        public String getUnlockedRoomId() {
            return unlockedRoomId;
        }
    }
}
