package domain.UseCases.GamePlay.QuickModeStartUp;

import application.game_registry.RoomRegistry;
import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInputData;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInteractor;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputData;
import application.use_cases.game_play.UserDataAccessInterface;
import domain.UseCases.GamePlay.ObjectsSetUp;
import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.CommonItem;
import domain.entities.Item.Item;
import domain.entities.Room.Position;
import domain.entities.Room.CommonRoom;

import domain.entities.Room.Room;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuickModeStartUpInteractorTest {

    private TestPresenter testPresenter;
    private TestUserDataAccess  testUserDataAccess;
    private QuickModeStartUpInteractor interactor;

    private String selectedRoomId;

    // Fake dataAccess
    private static class TestRoomRegistry implements RoomRegistry {
        private final Map<String, Room> rooms = new HashMap<>();

        public void addRoom(String id, Room room) {
            rooms.put(id, room);
        }

        @Override
        public Room getRoomById(String id) {
            return rooms.get(id);
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
        TestRoomRegistry testRegistry = new TestRoomRegistry();
        testUserDataAccess =  new TestUserDataAccess();
        interactor = new QuickModeStartUpInteractor(testPresenter, testRegistry, testUserDataAccess);

        testUserDataAccess.setCurrentUser(user);
        this.selectedRoomId = "room_01";

        // Set up Objects
        ObjectsSetUp objectsSetUp = new ObjectsSetUp();
        List<Hint> hints = objectsSetUp.hintSetUp();
        List<Item> items = objectsSetUp.itemSetUp();
        List<Interactable> interactables = objectsSetUp.interactableSetUp();
        Map<String, Position> positions = objectsSetUp.positionSetUp();

        Room selectedRoom = new CommonRoom(selectedRoomId,
                "description",
                "/images/ui/buttons/QuickButton.png",
                interactables,
                items,
                hints,
                positions);

        testRegistry.addRoom(selectedRoomId, selectedRoom);
    }

    @Test
    void testSuccessfulStartPreparesGameStartView() {


        //Mock the input data
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(selectedRoomId);

        interactor.execute(inputData);

        assertNull(testPresenter.getErrorMessage());

        QuickModeStartUpOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData, "Output data should not be null.");
        assertEquals(3, outputData.getObjectToDisplay().size());

        // Assert contents of display map
        ObjectsInfo doorInfo = outputData.getObjectToDisplay().get("door1");
        assertEquals("/images/ui/buttons/QuickButton.png", doorInfo.imgPath());
        assertEquals(new Position(10.0, 20.0), doorInfo.position());
    }

    @Test
    void testUserAlreadyHaveItem(){
        Item key = new CommonItem("key1",
                "/images/ui/buttons/QuickButton.png",
                "description",
                true,
                "/images/ui/buttons/QuickButton.png");

        User user = testUserDataAccess.getCurrentUser();
        user.setActiveGameMode("QuickMode");
        user.saveCurrentRoomID(selectedRoomId);
        user.saveItem(key);


        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(selectedRoomId);

        interactor.execute(inputData);

        QuickModeStartUpOutputData outputData = testPresenter.getSuccessData();

        assertEquals(2, outputData.getObjectToDisplay().size());

    }
    @Test
    void testNullOrEmptyRoomIdCallsPrepareFailView() {
        // Arrange
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData("   ");

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("Invalid room ID provided.", testPresenter.getErrorMessage());
    }

    @Test
    void testRoomNotFoundCallsPrepareFailView() {
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