package application.use_cases.interactable;

import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.interactable.interact.*;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.puzzle.*;
import domain.entities.room.CommonRoom;
import domain.entities.room.Position;
import domain.entities.room.Room;
import domain.entities.user.CommonUser;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InteractTest {

    private static class TestDataAccess implements InteractDataAccessInterface {
        private final Map<String, Room> rooms = new HashMap<>();
        private final Map<String, Interactable> interactables = new HashMap<>();
        private final Map<String, Item> items = new HashMap<>();
        private final Map<String, Puzzle> puzzles = new HashMap<>();

        @Override
        public Room getRoomById(String roomId) {
            return rooms.get(roomId);
        }

        @Override
        public Interactable getInteractableById(String interactableId) {
            return interactables.get(interactableId);
        }

        @Override
        public Item getItemById(String itemId) {
            return items.get(itemId);
        }

        @Override
        public Puzzle getPuzzleById(String puzzleId) {
            return puzzles.get(puzzleId);
        }

        public void addRoom(Room room) { rooms.put(room.getId(), room); }
        public void addInteractable(Interactable interactable) { interactables.put(interactable.getId(), interactable); }
        public void addItem(Item item) { items.put(item.getId(), item); }
        public void addPuzzle(Puzzle puzzle) { puzzles.put(puzzle.getId(), puzzle); }
    }

    private static class TestOutputBoundary implements InteractOutputBoundary {
        public String successMessage;
        public String failureMessage;
        public String roomImagePath;
        public Map<String, ObjectsInfo> objectsToDisplay;
        public boolean mainMenuCalled = false;

        @Override
        public void prepareSuccessView(InteractOutputData outputData) {
            this.successMessage = outputData.getSuccessMessage();
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            this.failureMessage = errorMessage;
        }

        @Override
        public void prepareRoomView(String imagePath, Map<String, ObjectsInfo> objectsToDisplay) {
            this.roomImagePath = imagePath;
            this.objectsToDisplay = objectsToDisplay;
        }

        @Override
        public void prepareMainMenuView() {
            this.mainMenuCalled = true;
        }
    }

    private static class TestUserDataAccess implements InteractUserDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    private TestDataAccess dataAccess;
    private TestOutputBoundary outputBoundary;
    private TestUserDataAccess userDataAccess;
    private InteractInteractor interactor;

    private CommonUser player;
    private CommonRoom room;
    private final String ROOM_ID = "room_1";
    private final String ROOM_IMAGE = "room_bg.png";
    private final String INTERACTABLE_ID = "interactable_1";
    private final String ITEM_ID = "item_1";
    private final String ITEM_NAME = "Rusty Key";
    private final String PUZZLE_ID = "puzzle_1";
    private final String SUCCESS_MSG = "You found a key!";

    @BeforeEach
    void setUp() {
        dataAccess = new TestDataAccess();
        outputBoundary = new TestOutputBoundary();
        userDataAccess = new TestUserDataAccess();

        player = new CommonUser("test_user", "password");
        player.setActiveGameMode("StoryMode");
        userDataAccess.setCurrentUser(player);

        room = new CommonRoom(
                ROOM_ID,
                "A test room",
                ROOM_IMAGE,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashMap<>()
        );
        dataAccess.addRoom(room);
        player.unlockRoom(room);
        player.switchRoom(room);

        Item item = new CommonItem(ITEM_ID, ITEM_NAME, "A rusty key", false, "key.png");
        dataAccess.addItem(item);

        interactor = new InteractInteractor(dataAccess, outputBoundary, userDataAccess);
    }

    private Interactable createChest(String id, boolean isConsumed, boolean consumesItem,
                                     boolean needsItem, String requiredItemId,
                                     String rewardItemId, String successMessage) {
        return new CommonInteractable(
                id,
                "Chest",
                "A locked chest",
                "chest_closed.png",
                "Open Chest",
                "An open chest",
                "chest_open.png",
                isConsumed,
                consumesItem,
                needsItem,
                requiredItemId,
                rewardItemId,
                null,
                null,
                successMessage
        );
    }

    private Interactable createDoor(String id, String unlockedRoomId) {
        return new CommonInteractable(
                id,
                "Door",
                "A door",
                "door.png",
                "Open Door",
                "An open door",
                "door_open.png",
                false,
                false,
                false,
                null,
                null,
                null,
                unlockedRoomId,
                null
        );
    }

    private Interactable createPuzzleInteractable(String id, String puzzleId) {
        return new CommonInteractable(
                id,
                "Puzzle Lock",
                "A lock with a puzzle",
                "lock.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                null,
                puzzleId,
                null,
                null
        );
    }

    @Test
    void testFirstInteraction_freeInteraction_success() {
        Interactable interactable = createChest(INTERACTABLE_ID, false, false, false, null, null, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertTrue(interactable.isInteracted());
        assertEquals(SUCCESS_MSG, outputBoundary.successMessage);
        assertEquals("Open Chest", interactable.getName());
        assertEquals("chest_open.png", interactable.getSprite());
    }

    @Test
    void testFirstInteraction_freeInteraction_withReward() {
        Interactable interactable = createChest(INTERACTABLE_ID, false, false, false, null, ITEM_ID, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertTrue(interactable.isInteracted());
        assertTrue(player.hasItemID(ITEM_ID));
        assertEquals(SUCCESS_MSG, outputBoundary.successMessage);
    }

    @Test
    void testFirstInteraction_consumedInteractable_removesFromRoom() {
        Interactable interactable = createChest(INTERACTABLE_ID, true, false, false, null, ITEM_ID, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertFalse(room.getInteractables().contains(interactable));
        assertTrue(player.hasItemID(ITEM_ID));
    }

    @Test
    void testFirstInteraction_itemRequired_success() {
        Interactable interactable = createChest(INTERACTABLE_ID, false, false, true, ITEM_ID, null, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        player.saveItem(dataAccess.getItemById(ITEM_ID));
        player.saveSelectedItemID(ITEM_ID);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertTrue(interactable.isInteracted());
        assertEquals(SUCCESS_MSG, outputBoundary.successMessage);
    }

    @Test
    void testFirstInteraction_itemRequired_wrongItem_failure() {
        String wrongItemId = "wrong_item";
        Item wrongItem = new CommonItem(wrongItemId, "Wrong Key", "Wrong key", false, "key.png");
        dataAccess.addItem(wrongItem);

        Interactable interactable = createChest(INTERACTABLE_ID, false, false, true, "correct_item", null, null);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        player.saveItem(wrongItem);
        player.saveSelectedItemID(wrongItemId);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertFalse(interactable.isInteracted());
        assertEquals("A different item is required for this interaction.", outputBoundary.failureMessage);
    }

    @Test
    void testFirstInteraction_itemRequired_noItemSelected_failure() {
        Interactable interactable = createChest(INTERACTABLE_ID, false, false, true, ITEM_ID, null, null);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);
        player.saveSelectedItemID(null);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertFalse(interactable.isInteracted());
        assertEquals("A specific item is required for this interaction.", outputBoundary.failureMessage);
    }

    @Test
    void testFirstInteraction_consumesItem_removesItemFromInventory() {
        Interactable interactable = createChest(INTERACTABLE_ID, true, true, true, ITEM_ID, null, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        player.saveItem(dataAccess.getItemById(ITEM_ID));
        player.saveSelectedItemID(ITEM_ID);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertFalse(player.hasItemID(ITEM_ID));
        assertTrue(interactable.isInteracted());
        assertFalse(room.getInteractables().contains(interactable));
    }

    @Test
    void testRepeatedInteraction_unlockedRoom_movesToRoom() {
        Room targetRoom = new CommonRoom(
                "target_room",
                "Target Room",
                "target_bg.png",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashMap<>()
        );
        dataAccess.addRoom(targetRoom);

        Interactable door = createDoor(INTERACTABLE_ID, "target_room");
        door.setInteracted(true);
        dataAccess.addInteractable(door);
        room.addInteractable(door);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals("target_room", player.getCurrentRoomID());
        assertNotNull(outputBoundary.roomImagePath);
    }

    @Test
    void testRepeatedInteraction_unlockedRoom_mainMenu_goesToMainMenu() {
        Interactable door = createDoor(INTERACTABLE_ID, "main menu");
        door.setInteracted(true);
        dataAccess.addInteractable(door);
        room.addInteractable(door);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertTrue(outputBoundary.mainMenuCalled);
    }

    @Test
    void testRepeatedInteraction_linkedPuzzle_solvedWithRoom_movesToRoom() {
        Room targetRoom = new CommonRoom(
                "target_room",
                "Target Room",
                "target_bg.png",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashMap<>()
        );
        dataAccess.addRoom(targetRoom);

        AnagramPuzzle puzzle = new AnagramPuzzle(
                PUZZLE_ID, "PDAISRE", "DESPAIR", "A feeling of hopelessness",
                "You solved it!", null, "target_room"
        );
        puzzle.setSolved(true);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createPuzzleInteractable(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals("target_room", player.getCurrentRoomID());
        assertNotNull(outputBoundary.roomImagePath);
    }

    @Test
    void testRepeatedInteraction_linkedPuzzle_solvedWithMainMenu_goesToMainMenu() {
        AnagramPuzzle puzzle = new AnagramPuzzle(
                PUZZLE_ID, "PDAISRE", "DESPAIR", "A feeling of hopelessness",
                "You solved it!", null, "main menu"
        );
        puzzle.setSolved(true);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createPuzzleInteractable(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertTrue(outputBoundary.mainMenuCalled);
    }

    @Test
    void testRepeatedInteraction_linkedPuzzle_solvedWithoutRoom_showsSuccessMessage() {
        String puzzleSuccess = "You solved the puzzle!";
        AnagramPuzzle puzzle = new AnagramPuzzle(
                PUZZLE_ID, "PDAISRE", "DESPAIR", "A feeling of hopelessness",
                puzzleSuccess, null, null
        );
        puzzle.setSolved(true);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createPuzzleInteractable(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals(puzzleSuccess, outputBoundary.successMessage);
    }

    @Test
    void testRepeatedInteraction_linkedPuzzle_unsolved_doesNothing() {
        AnagramPuzzle puzzle = new AnagramPuzzle(
                PUZZLE_ID, "PDAISRE", "DESPAIR", "A feeling of hopelessness",
                "You solved it!", null, null
        );
        puzzle.setSolved(false);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createPuzzleInteractable(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertNull(outputBoundary.successMessage);
        assertNull(outputBoundary.failureMessage);
        assertNull(outputBoundary.roomImagePath);
        assertFalse(outputBoundary.mainMenuCalled);
    }

    @Test
    void testRepeatedInteraction_readableWithReward_showsAlreadyMessage() {
        Interactable interactable = new CommonInteractable(
                INTERACTABLE_ID,
                "Chest",
                "A chest",
                "chest.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                ITEM_ID,
                null,
                null,
                "You have obtained a Rusty Key."
        );
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals("You have obtained a Rusty Key already!", outputBoundary.successMessage);
    }

    @Test
    void testRepeatedInteraction_readableWithoutReward_showsOriginalMessage() {
        Interactable interactable = new CommonInteractable(
                INTERACTABLE_ID,
                "Note",
                "A note",
                "note.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                "The note says: 'The key is under the mat.'"
        );
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals("The note says: 'The key is under the mat.'", outputBoundary.successMessage);
    }

    @Test
    void testRepeatedInteraction_readableWithExclamationMark_handlesPunctuationCorrectly() {
        Interactable interactable = new CommonInteractable(
                INTERACTABLE_ID,
                "Chest",
                "A chest",
                "chest.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                ITEM_ID,
                null,
                null,
                "You found a key!"
        );
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertEquals("You found a key already!", outputBoundary.successMessage);
    }

    @Test
    void testInteractWithNullInteractable_doesNotThrow() {
        assertDoesNotThrow(() -> {
            interactor.interact(new InteractInputData("non_existent_id"));
        });
    }

    @Test
    void testInteractWithNullUser_doesNotThrow() {
        userDataAccess.setCurrentUser(null);

        Interactable interactable = createChest(INTERACTABLE_ID, false, false, false,
                null, null, SUCCESS_MSG);
        dataAccess.addInteractable(interactable);
        room.addInteractable(interactable);

        assertDoesNotThrow(() -> {
            interactor.interact(new InteractInputData(INTERACTABLE_ID));
        });
    }

    @Test
    void testInteractWithUnlockedRoom_handlesPositionMapping() {
        Room targetRoom = new CommonRoom(
                "target_room",
                "Target Room",
                "target_bg.png",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashMap<>()
        );
        targetRoom.setPosition("interactable_2", new Position(100.0, 200.0));
        dataAccess.addRoom(targetRoom);

        Interactable targetInteractable = new CommonInteractable(
                "interactable_2",
                "Table",
                "A table",
                "table.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                null
        );
        targetRoom.addInteractable(targetInteractable);
        dataAccess.addInteractable(targetInteractable);

        Interactable door = createDoor(INTERACTABLE_ID, "target_room");
        door.setInteracted(true);
        dataAccess.addInteractable(door);
        room.addInteractable(door);

        interactor.interact(new InteractInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.objectsToDisplay);
        assertTrue(outputBoundary.objectsToDisplay.containsKey("interactable_2"));
        ObjectsInfo info = outputBoundary.objectsToDisplay.get("interactable_2");
        assertEquals("table.png", info.imgPath());
        assertNotNull(info.position());
    }
}
