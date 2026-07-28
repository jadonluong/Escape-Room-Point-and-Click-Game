package domain.UseCases.GamePlay.QuickModeStartUp;

import application.game_registry.RoomRegistry;
import application.use_cases.GamePlay.ObjectsInfo;
import application.use_cases.GamePlay.QuickPlay.QuickModeStartUp.QuickModeStartUpInputData;
import application.use_cases.GamePlay.QuickPlay.QuickModeStartUp.QuickModeStartUpInteractor;
import application.use_cases.GamePlay.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputData;
import domain.entities.Hint.Hint;
import domain.entities.Interactable.CommonInteractable;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.CommonItem;
import domain.entities.Hint.CommonHint;
import domain.entities.Item.Item;
import domain.entities.Room.Position;
import domain.entities.Room.CommonRoom;

import domain.entities.Room.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuickModeStartUpInteractorTest {

    private TestPresenter testPresenter;
    private TestRoomRegistry testRegistry;
    private QuickModeStartUpInteractor interactor;

    // Fake RoomRegistry to control what getRoomByID returns without Mockito
    private static class TestRoomRegistry implements RoomRegistry {
        private final Map<String, Room> rooms = new HashMap<>();

        public void addRoom(String id, Room room) {
            rooms.put(id, room);
        }

        @Override
        public Room getRoomByID(String id) {
            return rooms.get(id);
        }
    }

    @BeforeEach
    void setUp() {
        testPresenter = new TestPresenter();
        testRegistry = new TestRoomRegistry();
        interactor = new QuickModeStartUpInteractor(testPresenter, testRegistry);
    }

    @Test
    void execute_SuccessfulStart_PreparesGameStartView() {
        // Arrange
        String roomId = "room_01";

        List<Interactable> interactables = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Hint> hints = new ArrayList<>();
        // Setup dummy domain objects
        Interactable door = new CommonInteractable(
                "door1",                           // String id
                "/images/ui/buttons/QuickButton.png",                 // String imagePath
                "Wooden Door",                     // String defaultName
                "A sturdy wooden door.",           // String defaultDescription
                "/images/ui/buttons/QuickButton.png",          // String defaultSprite
                "Unlocked Door",                   // String interactedName
                "The door is now wide open.",      // String interactedDescription
                "/images/ui/buttons/TutorialButton.png",            // String interactedSprite
                false,                             // boolean isConsumed
                true,                              // boolean consumesItem
                true,                              // boolean needsItem
                "brass_key",                       // String requiredItemId
                "reward_coin",                     // String rewardItemId
                "puzzle_01",                       // String linkedPuzzleId
                "room_02",                         // String unlockedRoomId
                "You used the key and unlocked the door!" // String successMessage
        );

        List<String> messages = new ArrayList<>();
        messages.add("gigty");
        messages.add("gigty, gigty");
        Item key = new CommonItem("key1",
                "/images/ui/buttons/QuickButton.png",
                "gigity",
                true,
                "/images/ui/buttons/QuickButton.png");
        Hint hint = new CommonHint("hint1",
                "/images/ui/buttons/QuickButton.png",
                messages);

        Position pos1 = new Position(10, 20);
        Position pos2 = new Position(30, 40);
        Position pos3 = new Position(50, 60);

        // Build a room populated with entities and positions

        interactables.add(door);
        items.add(key);
        hints.add(hint);


        Room room = new CommonRoom(roomId,
                "gigity",
                "/images/ui/buttons/QuickButton.png",
                interactables,
                items,
                hints);
        room.setPosition(door.getId(),pos1);
        room.setPosition(key.getId(),pos2);
        room.setPosition(hint.getObjectID(), pos3);
        testRegistry.addRoom(roomId, room);

        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(roomId);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getErrorMessage(), "Error message should be null on success.");

        QuickModeStartUpOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData, "Output data should not be null.");
        assertEquals(3, outputData.getObjectToDisplay().size());

        // Assert contents of display map
        ObjectsInfo doorInfo = outputData.getObjectToDisplay().get("door1");
        assertEquals("/images/ui/buttons/QuickButton.png", doorInfo.imgPath());
        assertEquals(pos1, doorInfo.position());
    }

    @Test
    void execute_NullOrEmptyRoomId_CallsPrepareFailView() {
        // Arrange
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData("   ");

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("Invalid room ID provided.", testPresenter.getErrorMessage());
    }

    @Test
    void execute_RoomNotFound_CallsPrepareFailView() {
        // Arrange
        String roomId = "non_existent_room";
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(roomId);

        // Act (Registry has no room added)
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("Could not load room with ID: " + roomId, testPresenter.getErrorMessage());
    }
}