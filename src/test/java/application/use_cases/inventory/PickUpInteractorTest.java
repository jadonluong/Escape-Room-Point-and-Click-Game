package application.use_cases.inventory;

import application.game_registry.ItemRegistry;
import application.use_cases.item.pick_up.PickUpInteractor;
import application.use_cases.item.pick_up.PickUpUserDataAccessInterface;
import application.use_cases.item.pick_up.PickUpInputData;
import application.use_cases.item.pick_up.PickUpOutputData;
import application.use_cases.item.pick_up.PickUpOutputBoundary;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.user.CommonUser;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PickUpInteractorTest {

    private TestUserDataAccess userSession;
    private TestItemRegistry itemRegistry;
    private TestPresenter testPresenter;
    private PickUpInteractor interactor;

    private TestUser testUser;
    private Item testItem;

    private static class TestUser extends CommonUser {
        private Item savedItem;

        public TestUser(String username, String password) {
            super(username, password);
        }

        @Override
        public void saveItem(Item item) {
            this.savedItem = item;
        }

        public Item getSavedItem() {
            return savedItem;
        }
    }

    private static class TestUserDataAccess implements PickUpUserDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    private static class TestItemRegistry implements ItemRegistry {
        private final Map<String, Item> registryMap = new HashMap<>();

        public void addItem(Item item) {
            registryMap.put(item.getId(), item);
        }

        @Override
        public Item getItemById(String id) {
            return registryMap.get(id);
        }
    }

    private static class TestPresenter implements PickUpOutputBoundary {
        private PickUpOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(PickUpOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public PickUpOutputData getSuccessData() {
            return successData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    @BeforeEach
    void setup() {
        userSession = new TestUserDataAccess();
        itemRegistry = new TestItemRegistry();
        testPresenter = new TestPresenter();

        interactor = new PickUpInteractor(userSession, itemRegistry, testPresenter);

        testUser = new TestUser("TestUser", "password123");
        userSession.setCurrentUser(testUser);

        testItem = new CommonItem("prison_item_1", "stick", "A wooden stick", true, "stick.png");
        itemRegistry.addItem(testItem);
    }

    @Test
    void testExecuteSuccessSavesItemAndUpdatesPresenter() {
        PickUpInputData inputData = new PickUpInputData("prison_item_1");

        interactor.execute(inputData);

        assertEquals(testItem, testUser.getSavedItem());
        assertNotNull(testPresenter.getSuccessData());
    }

    @Test
    void testExecuteWhenItemNotFoundDoesNothing() {
        PickUpInputData inputData = new PickUpInputData("invalid_item_id");

        interactor.execute(inputData);

        assertNull(testUser.getSavedItem());
        assertNull(testPresenter.getSuccessData());
    }

    @Test
    void testExecuteWhenNoUserLoggedInStillUpdatesPresenter() {
        userSession.setCurrentUser(null);
        PickUpInputData inputData = new PickUpInputData("prison_item_1");

        interactor.execute(inputData);

        assertNotNull(testPresenter.getSuccessData());
    }
}