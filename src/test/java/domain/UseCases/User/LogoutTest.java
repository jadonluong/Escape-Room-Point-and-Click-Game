package domain.UseCases.User;

import application.use_cases.User.Logout.*;
import domain.entities.User.CommonUser;
import domain.entities.User.GuestUser;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest {
    private TestLogoutPresenter testPresenter;
    private TestLogoutDataAccess dataAccess;
    private LogoutInteractor interactor;

    private User user;
    private String commonUserName;

    private static class TestLogoutPresenter implements LogoutOutputBoundary {
        private LogoutOutputData successData;

        @Override
        public void prepareUnsavedSuccessView(LogoutOutputData logoutOutputData) {
            this.successData = logoutOutputData;
        }

        public LogoutOutputData getSuccessData() {return this.successData;}
    }

    private static class TestLogoutDataAccess implements LogoutUserDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return this.currentUser;
        }

        @Override
        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    @BeforeEach
    void setup() {
        testPresenter = new TestLogoutPresenter();
        dataAccess = new TestLogoutDataAccess();
        interactor = new LogoutInteractor(dataAccess, testPresenter);

        commonUserName = "Nobara";
        user = new CommonUser(commonUserName, "Nailed it");
        dataAccess.setCurrentUser(user);
    }

    @Test
    void successTestCommonUser() {
        LogoutInputData logoutInputData = new LogoutInputData(commonUserName);
        interactor.execute(logoutInputData);

        assertInstanceOf(GuestUser.class, dataAccess.getCurrentUser());

        assertNotNull(testPresenter.getSuccessData());
        assertFalse(testPresenter.getSuccessData().isLogoutFailed());
        assertEquals(testPresenter.getSuccessData().getUsername(), commonUserName);
    }

    @Test
    void successTestGuestUser() {
        user = new GuestUser();
        LogoutInputData logoutInputData = new LogoutInputData(user.getUsername());
        interactor.execute(logoutInputData);

        assertInstanceOf(GuestUser.class, dataAccess.getCurrentUser());

        assertNotNull(testPresenter.getSuccessData());
        assertFalse(testPresenter.getSuccessData().isLogoutFailed());
        assertEquals(testPresenter.getSuccessData().getUsername(), user.getUsername());
    }
}
