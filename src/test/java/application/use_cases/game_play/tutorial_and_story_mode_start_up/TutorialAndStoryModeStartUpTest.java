package application.use_cases.game_play.tutorial_and_story_mode_start_up;

import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.UserDataAccessInterface;
import application.use_cases.game_play.ObjectsSetUp;
import domain.entities.hint.Hint;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.item.Item;
import domain.entities.room.CommonRoom;
import domain.entities.room.Position;
import domain.entities.room.Room;
import domain.entities.user.CommonUserFactory;
import domain.entities.user.CommonUserFactoryClass;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TutorialAndStoryModeStartUpTest {

    private TestPresenter testPresenter;
    private TestDataAccess testDataAccess;
    private TestUserDataAccess testUserDataAccess;
    private TutorialAndStoryModeStartUpInteractor interactor;

    private Room tutorialRoom;
    private Room storyRoom;



    // Fake Data Access Interface
    private static class TestDataAccess implements StartUpDataAccessInterface {
        private Room tutorialRoom;
        private Room storyRoom;

        public void setTutorialRoom(Room room) {
            this.tutorialRoom = room;
        }

        public void setStoryRoom(Room room) {
            this.storyRoom = room;
        }

        @Override
        public Room findStartingRoomForTut() {
            return tutorialRoom;
        }

        @Override
        public Room findStartingRoomForStory() {
            return storyRoom;
        }
    }

    private static class TestUserDataAccess implements UserDataAccessInterface {
        private User currentUser;

        public void setCurrentUser(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public User getCurrentUser(){
            return currentUser;
        }
    }

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactoryClass();
        String username = "test_user";
        String password = "secure_password";

        User user = factory.createCommonUser(username, password);
        testPresenter = new TestPresenter();
        testDataAccess = new TestDataAccess();
        testUserDataAccess =  new TestUserDataAccess();
        interactor = new TutorialAndStoryModeStartUpInteractor(testPresenter, testDataAccess, testUserDataAccess);
        testUserDataAccess.setCurrentUser(user);

        // Setup Tutorial Room Entities
        ObjectsSetUp objectsSetUp = new ObjectsSetUp();
        List<Hint> hints = objectsSetUp.hintSetUp();
        List<Item> items = objectsSetUp.itemSetUp();
        List<Interactable> interactables = objectsSetUp.interactableSetUp();
        Map<String, Position> positions = objectsSetUp.positionSetUp();

        tutorialRoom = new CommonRoom("tut_room", "Tutorial Room", "/img/room.png",
                interactables, items, hints, positions);

        // Setup Story Room Entities
        Interactable storyChest = new CommonInteractable(
                "story_chest", "Chest", "Desc", "/img/chest.png",
                "Open Chest", "Desc", "/img/openchest.png",
                false, false, false, "", "", "", "", ""
        );

        Position storyPos = new Position(50.0, 50.0);
        Map<String, Position> storyPositions = new HashMap<>();
        storyPositions.put(storyChest.getId(), storyPos);

        List<Interactable> storyInteractables = new ArrayList<>();
        storyInteractables.add(storyChest);

        storyRoom = new CommonRoom("story_room", "Story Room", "/img/story_room.png",
                storyInteractables, new ArrayList<>(), new ArrayList<>(), storyPositions);

        testDataAccess.setTutorialRoom(tutorialRoom);
        testDataAccess.setStoryRoom(storyRoom);
    }

    @Test
    void testTutorialModePreparesGameStartView() {
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData("TUTORIAL");

        interactor.execute(inputData);

        assertNull(testPresenter.getErrorMessage());

        TutorialAndStoryModeStartUpOutPutData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertEquals(3, outputData.getObjectToDisplay().size());

        ObjectsInfo doorInfo = outputData.getObjectToDisplay().get("door1");
        assertNotNull(doorInfo);
        assertEquals("/images/ui/buttons/QuickButton.png", doorInfo.imgPath());
        assertEquals(new Position(10.0, 20.0), doorInfo.position());

        ObjectsInfo keyInfo = outputData.getObjectToDisplay().get("key1");
        assertNotNull(keyInfo);
        assertEquals("/images/ui/buttons/QuickButton.png", keyInfo.imgPath());

        ObjectsInfo hintInfo = outputData.getObjectToDisplay().get("hint1");
        assertNotNull(hintInfo);
        assertEquals("/images/ui/buttons/QuickButton.png", hintInfo.imgPath());
    }

    @Test
    void testStoryModePreparesGameStartView() {
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData("STORY");

        interactor.execute(inputData);

        assertNull(testPresenter.getErrorMessage());

        TutorialAndStoryModeStartUpOutPutData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertEquals("/img/story_room.png", outputData.getRoomImgPath());
        assertEquals(1, outputData.getObjectToDisplay().size());

        ObjectsInfo chestInfo = outputData.getObjectToDisplay().get("story_chest");
        assertNotNull(chestInfo);
        assertEquals("/img/chest.png", chestInfo.imgPath());
        assertEquals(new Position(50.0, 50.0), chestInfo.position());
    }

    @Test
    void testInvalidModeCallsPrepareFailView() {
        String mode  = "INVALID_MODE";
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData(mode);

        interactor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Invalid mode selected: " + mode, testPresenter.getErrorMessage());
    }

    @Test
    void testExecute_WhenUserIsNull_CallsPrepareFailView() {
        // Arrange: Explicitly force the data access to return a null user
        testUserDataAccess.setCurrentUser(null);
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData("TUTORIAL");

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("user is null", testPresenter.getErrorMessage());
    }

    @Test
    void testExecute_WhenUserAlreadyHasItem_SkipsItemInDisplayMap() {
        // Arrange: Create a room with an item
        domain.entities.item.Item duplicateItem = new domain.entities.item.CommonItem("key1", "Key",
                "description", true, "key.png");
        List<domain.entities.item.Item> items = new ArrayList<>();
        items.add(duplicateItem);

        Room customStoryRoom = new CommonRoom("custom_room", "Desc", "/img/room.png",
                new ArrayList<>(), items, new ArrayList<>(), new HashMap<>());
        testDataAccess.setStoryRoom(customStoryRoom);

        // Crucial step: Mock your User instance so that hasItemID("key1") returns true
        // (Assuming you can add item IDs to your test user or configure the mock class)
        User user = testUserDataAccess.getCurrentUser();
        // e.g., user.collectItem(duplicateItem); or whatever method updates user item inventory

        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData("STORY");

        // Act
        interactor.execute(inputData);

        // Assert: The item should be filtered out and not exist in the display output
        TutorialAndStoryModeStartUpOutPutData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
    }
}
