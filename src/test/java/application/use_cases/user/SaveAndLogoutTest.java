package application.use_cases.user;

import application.use_cases.user.logout.LogoutUserDataAccessInterface;
import application.use_cases.user.save_and_logout.SaveAndLogoutInputData;
import application.use_cases.user.save_and_logout.SaveAndLogoutInteractor;
import application.use_cases.user.save_and_logout.SaveAndLogoutOutputBoundary;
import application.use_cases.user.save_and_logout.SaveAndLogoutOutputData;
import application.use_cases.user.save_progress.SaveProgressUserDataAccessInterface;
import domain.entities.user.CommonUser;
import domain.entities.user.GuestUser;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveAndLogoutTest {
    private TestSaveProgressDataAccess saveProgressDataAccess;
    private TestLogoutDataAccess logoutDataAccess;
    private TestPresenter testPresenter;
    private SaveAndLogoutInteractor interactor;

    private String testUsername;

    private static class TestSaveProgressDataAccess implements SaveProgressUserDataAccessInterface {
        private String savedUsername;

        @Override
        public void saveProgress(CommonUser user) {
            this.savedUsername = user.getUsername();
        }

        public String getSavedUsername() {
            return savedUsername;
        }
    }

    private static class TestLogoutDataAccess implements LogoutUserDataAccessInterface {
        private User user;

        @Override
        public User getCurrentUser() {
            return user;
        }

        @Override
        public void setCurrentUser(User user) {
            this.user = user;
        }
    }

    private static class TestPresenter implements SaveAndLogoutOutputBoundary {
        private SaveAndLogoutOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSavedSuccessView(SaveAndLogoutOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public SaveAndLogoutOutputData getSuccessData() {
            return successData;
        }
    }

    @BeforeEach
    void setUp() {
        saveProgressDataAccess = new TestSaveProgressDataAccess();
        logoutDataAccess = new TestLogoutDataAccess();
        testPresenter = new TestPresenter();
        interactor = new SaveAndLogoutInteractor(saveProgressDataAccess, logoutDataAccess, testPresenter);

        testUsername = "Todo";
        User testUser = new CommonUser(testUsername, "Takada-chan");
        logoutDataAccess.setCurrentUser(testUser);
    }

    @Test
    void successTest() {
        SaveAndLogoutInputData saveAndLogoutInputData = new SaveAndLogoutInputData(testUsername);
        interactor.execute(saveAndLogoutInputData);

        assertNull(testPresenter.getErrorMessage());
        assertEquals(testUsername, saveProgressDataAccess.getSavedUsername());
        assertNull(logoutDataAccess.getCurrentUser());
        assertFalse(testPresenter.getSuccessData().isUseCaseFailed());
        assertEquals(testUsername, testPresenter.getSuccessData().getUsername());
    }

    @Test
    void testUsernameMismatch() {
        SaveAndLogoutInputData saveAndLogoutInputData = new SaveAndLogoutInputData("Sukuna");
        interactor.execute(saveAndLogoutInputData);

        assertNull(testPresenter.getSuccessData());
        assertNull(saveProgressDataAccess.getSavedUsername());
        assertEquals("You are not the current user", testPresenter.getErrorMessage());
    }

    @Test
    void testGuestUserCannotSave() {
        User guestUser = new GuestUser();
        logoutDataAccess.setCurrentUser(guestUser);

        SaveAndLogoutInputData inputData = new SaveAndLogoutInputData(logoutDataAccess.getCurrentUser().getUsername());
        interactor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertNull(saveProgressDataAccess.getSavedUsername());
        assertEquals("user is in Guest Mode, progress cannot be saved.", testPresenter.getErrorMessage());
    }
}