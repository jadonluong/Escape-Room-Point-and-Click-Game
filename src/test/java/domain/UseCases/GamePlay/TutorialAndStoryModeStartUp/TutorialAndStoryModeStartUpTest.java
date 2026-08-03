package domain.UseCases.GamePlay.TutorialAndStoryModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;
import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.*;
import application.use_cases.GamePlay.UserDataAccessInterface;
import domain.entities.Hint.CommonHint;
import domain.entities.Hint.Hint;
import domain.entities.Interactable.CommonInteractable;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.CommonItem;
import domain.entities.Item.Item;
import domain.entities.Room.CommonRoom;
import domain.entities.Room.Position;
import domain.entities.Room.Room;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import domain.entities.User.User;
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
        Interactable tutDoor = new CommonInteractable(
                "tut_door", "Door", "Desc", "/img/door.png",
                "Open Door", "Desc", "/img/opendoor.png",
                false, false, false, "", "", "", "", ""
        );
        Item tutKey = new CommonItem("tut_key", "/img/key.png", "A key", true, "/img/key.png");

        List<String> hintMsgs = new ArrayList<>();
        hintMsgs.add("Look around");
        Hint tutHint = new CommonHint("tut_hint", "/img/hint.png", hintMsgs);

        Position pos1 = new Position(10.0, 10.0);
        Position pos2 = new Position(20.0, 20.0);
        Position pos3 = new Position(30.0, 30.0);

        Map<String, Position> tutPositions = new HashMap<>();
        tutPositions.put(tutDoor.getId(), pos1);
        tutPositions.put(tutKey.getId(), pos2);
        tutPositions.put(tutHint.getObjectID(), pos3);

        List<Interactable> tutInteractables = new ArrayList<>();
        tutInteractables.add(tutDoor);
        List<Item> tutItems = new ArrayList<>();
        tutItems.add(tutKey);
        List<Hint> tutHints = new ArrayList<>();
        tutHints.add(tutHint);

        tutorialRoom = new CommonRoom("tut_room", "Tutorial Room", "/img/room.png",
                tutInteractables, tutItems, tutHints, tutPositions);

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

        ObjectsInfo doorInfo = outputData.getObjectToDisplay().get("tut_door");
        assertNotNull(doorInfo);
        assertEquals("/img/door.png", doorInfo.imgPath());
        assertEquals(new Position(10.0, 10.0), doorInfo.position());

        ObjectsInfo keyInfo = outputData.getObjectToDisplay().get("tut_key");
        assertNotNull(keyInfo);
        assertEquals("/img/key.png", keyInfo.imgPath());

        ObjectsInfo hintInfo = outputData.getObjectToDisplay().get("tut_hint");
        assertNotNull(hintInfo);
        assertEquals("/img/hint.png", hintInfo.imgPath());
    }

    @Test
    void testStoryModePreparesGameStartView() {
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData("STORY");

        interactor.execute(inputData);

        assertNull(testPresenter.getErrorMessage());

        TutorialAndStoryModeStartUpOutPutData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
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
}
