package domain.UseCases.inventory;

import application.use_cases.Item.SelectItem.*;

import domain.entities.User.CommonUser;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SelectItemInteractorTest {
    private TestUserDataAccess userSession;
    private TestPresenter testPresenter;
    private SelectItemInteractor interactor;
    private TestUser testUser;

    private static class TestUser extends CommonUser {
        private String savedItemId;

        public TestUser(String username, String password) {
            super(username, password);
        }

        @Override
        public void saveSelectedItemID(String itemId) {
            this.savedItemId = itemId;
        }

        public String getSavedItemId() {
            return savedItemId;
        }
    }

    private static class TestUserDataAccess implements SelectItemUserDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    private static class TestPresenter implements SelectItemOutputBoundary {
        private SelectItemOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SelectItemOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public SelectItemOutputData getSuccessData() {
            return successData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    @BeforeEach
    void setup() {
        userSession = new TestUserDataAccess();
        testPresenter = new TestPresenter();
        interactor = new SelectItemInteractor(userSession, testPresenter);

        testUser = new TestUser("TestUser", "password123");
        userSession.setCurrentUser(testUser);
    }

    @Test
    void testExecuteSuccessSavesItemIdAndUpdatesPresenter() {
        SelectItemInputData inputData = new SelectItemInputData("prison_item_1:stick");

        interactor.execute(inputData);

        assertEquals("prison_item_1:stick", testUser.getSavedItemId());
        assertNotNull(testPresenter.getSuccessData());
        assertEquals("prison_item_1:stick", testPresenter.getSuccessData().getSelectedItemId());
        assertNull(testPresenter.getErrorMessage());
    }

    @Test
    void testExecuteWhenNoUserLoggedInDoesNothing() {
        userSession.setCurrentUser(null);
        SelectItemInputData inputData = new SelectItemInputData("prison_item_1:stick");

        interactor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
    }
}