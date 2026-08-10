package application.use_cases.game_play.quick_play.quick_mode_start_up;

import application.game_registry.RoomRegistry;
import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.UserDataAccessInterface;
import application.use_cases.game_play.ObjectsSetUp;
import domain.entities.hint.Hint;
import domain.entities.interactable.Interactable;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.room.Position;
import domain.entities.room.CommonRoom;

import domain.entities.room.Room;
import domain.entities.user.CommonUserFactory;
import domain.entities.user.CommonUserFactoryClass;
import domain.entities.user.User;
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

    @Test
    void testOutputDataGetRoomImgPath() {
        // Arrange
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(selectedRoomId);

        // Act
        interactor.execute(inputData);

        // Assert: Explicitly call getRoomImgPath() to complete QuickModeStartUpOutputData coverage
        QuickModeStartUpOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertEquals("/images/ui/buttons/QuickButton.png", outputData.getRoomImgPath());
    }

    @Test
    void testExecute_WhenCurrentUserIsNull_CallsPrepareFailView() {
        // Arrange: Explicitly remove the user to trip the first guard clause
        testUserDataAccess.setCurrentUser(null);
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(selectedRoomId);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("No user selected", testPresenter.getErrorMessage());
    }

    @Test
    void testExecute_WhenRoomIdIsNull_CallsPrepareFailView() {
        // Arrange: Test the null branch of the roomId check
        QuickModeStartUpInputData inputData = new QuickModeStartUpInputData(null);

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(testPresenter.getSuccessData());
        assertEquals("Invalid room ID provided.", testPresenter.getErrorMessage());
    }
}