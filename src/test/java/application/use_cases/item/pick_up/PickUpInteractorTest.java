package application.use_cases.item.pick_up;

import application.game_registry.ItemRegistry;
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
    private TestPresenter presenter;
    private PickUpInteractor interactor;

    private TestUser testUser;
    private Item testItem;

    // Test double for User to track saved items
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

    // Test double for User Data Access / Session
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

    // Test double for ItemRegistry
    private static class TestItemRegistry implements ItemRegistry {
        private final Map<String, Item> items = new HashMap<>();

        public void addItem(Item item) {
            items.put(item.getId(), item);
        }

        @Override
        public Item getItemById(String id) {
            return items.get(id);
        }
    }

    // Test double for Presenter
    private static class TestPresenter implements PickUpOutputBoundary {
        private PickUpOutputData successData;

        @Override
        public void prepareSuccessView(PickUpOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {

        }

        public PickUpOutputData getSuccessData() {
            return successData;
        }
    }

    @BeforeEach
    void setup() {
        userSession = new TestUserDataAccess();
        itemRegistry = new TestItemRegistry();
        presenter = new TestPresenter();
        interactor = new PickUpInteractor(userSession, itemRegistry, presenter);

        testUser = new TestUser("Choso", "Yuji");
        userSession.setCurrentUser(testUser);

        testItem = new CommonItem("prisonitem_1", "stick", "A wooden stick", true, "stick.png");
        itemRegistry.addItem(testItem);
    }

    @Test
    void testSuccessfulPickUpSavesItemToUserAndNotifiesPresenter() {
        PickUpInputData inputData = new PickUpInputData("prisonitem_1");

        interactor.execute(inputData);

        assertEquals(testItem, testUser.getSavedItem());
        assertNotNull(presenter.getSuccessData());
        assertEquals(testItem, presenter.getSuccessData().getItemName());
    }

    @Test
    void testItemNotFoundInRegistryDoesNothing() {
        PickUpInputData inputData = new PickUpInputData("non_existent_item");

        interactor.execute(inputData);

        assertNull(testUser.getSavedItem());
        assertNull(presenter.getSuccessData());
    }

    @Test
    void testPickUpWhenNoUserLoggedInStillNotifiesPresenter() {
        userSession.setCurrentUser(null);
        PickUpInputData inputData = new PickUpInputData("prisonitem_1");

        interactor.execute(inputData);

        assertNotNull(presenter.getSuccessData());
        assertEquals(testItem, presenter.getSuccessData().getItemName());
    }
}