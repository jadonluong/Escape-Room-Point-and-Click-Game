package domain.UseCases.User;

import application.use_cases.User.SaveProgress.*;
import domain.entities.User.CommonUser;
import domain.entities.User.GuestUser;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveProgressTest {
    private TestSaveProgressPresenter testPresenter;
    private TestUserDataAccess userDataAccess;
    private TestSessionDataAccess sessionDataAccess;
    private SaveProgressInteractor saveProgressInteractor;

    private User testUser;
    private String testUsername;

    private static class TestSaveProgressPresenter implements SaveProgressOutputBoundary {
        private SaveProgressOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SaveProgressOutputData saveProgressOutputData) {
            this.successData = saveProgressOutputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public SaveProgressOutputData getSuccessData() {return successData;}

        public String getErrorMessage() {return errorMessage;}
    }

    private static class TestUserDataAccess implements SaveProgressUserDataAccessInterface {
        private String savedUsername;

        @Override
        public void saveProgress(CommonUser user) {
            savedUsername = user.getUsername();
        }

        public String getSavedUsername() {return savedUsername;}
    }

    private static class TestSessionDataAccess implements SaveProgressUserSessionDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    @BeforeEach
    void setUp() {
        testPresenter = new TestSaveProgressPresenter();
        userDataAccess = new TestUserDataAccess();
        sessionDataAccess = new TestSessionDataAccess();
        saveProgressInteractor = new SaveProgressInteractor(testPresenter, userDataAccess, sessionDataAccess);

        testUsername = "Nanami";
        testUser = new CommonUser(testUsername, "Malaysia");
        sessionDataAccess.setCurrentUser(testUser);
    }

    @Test
    void testUsernameMismatch() {
        SaveProgressInputData saveProgressInputData = new SaveProgressInputData("Haibara");
        saveProgressInteractor.execute(saveProgressInputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Invalid username provided.", testPresenter.getErrorMessage());
    }

    @Test
    void testGuestUserCannotSave() {
        User testGuestUser = new GuestUser();
        sessionDataAccess.setCurrentUser(testGuestUser);
        SaveProgressInputData saveProgressInputData = new SaveProgressInputData(testGuestUser.getUsername());
        saveProgressInteractor.execute(saveProgressInputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("User is in Guest Mode, progress cannot be saved.", testPresenter.getErrorMessage());
    }

    @Test
    void successTest() {
        SaveProgressInputData saveProgressInputData = new SaveProgressInputData(testUsername);
        saveProgressInteractor.execute(saveProgressInputData);

        assertNull(testPresenter.getErrorMessage());
        assertEquals(testUsername, userDataAccess.getSavedUsername());
        assertFalse(testPresenter.getSuccessData().isSaveFailed());
        assertEquals(testUsername, testPresenter.getSuccessData().getUsername());
    }
}
