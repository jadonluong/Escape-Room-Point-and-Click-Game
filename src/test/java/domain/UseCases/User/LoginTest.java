package domain.UseCases.User;

import application.use_cases.User.Login.*;
import domain.entities.User.CommonUser;
import domain.entities.User.CommonUserFunction;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private TestUserDataAccess userDAO;
    private TestUserSessionDataAccess sessionDataAccess;
    private TestPresenter testPresenter;
    private LoginInteractor loginInteractor;

    private String username;
    private String password;

    private static class TestUserDataAccess implements LoginUserDataAccessInterface {
        private Map<String, HashMap<String, String>> testUserDB;

        @Override
        public CommonUserFunction getUserPassword(String username) {
            HashMap<String, String> userInfo = testUserDB.get(username);
            return new CommonUser(username, userInfo.get(username));
        }

        @Override
        public CommonUser getUser(String username) {
            HashMap<String, String> userInfo = testUserDB.get(username);
            return new CommonUser(username, userInfo.get(username));
        }

        @Override
        public boolean existsByName(String username) {
            return testUserDB.containsKey(username);
        }

        public void setTestUserDB(Map<String, HashMap<String, String>> testUserDB) {
            this.testUserDB = testUserDB;
        }
    }

    private static class  TestUserSessionDataAccess implements LoginUserSessionDataAccessInterface {
        private User currentUser;

        @Override
        public void setCurrentUser(User user) {
            this.currentUser = user;
        }

        public String getCurrentUserId() {
            return currentUser.getUsername();
        }
    }

    private static class TestPresenter implements LoginOutputBoundary {
        private LoginOutputData successData;
        private String errorMessage;

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            this.successData = outputData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public LoginOutputData getSuccessData() {
            return successData;
        }
    }

    @BeforeEach
    void setup() {
        userDAO = new TestUserDataAccess();
        sessionDataAccess = new TestUserSessionDataAccess();
        testPresenter = new TestPresenter();
        loginInteractor = new LoginInteractor(userDAO, sessionDataAccess, testPresenter);

        this.username = "Choso";
        this.password = "Yuji";

        Map<String, HashMap<String, String>> userDB = new HashMap<>();
        HashMap<String, String> user_1 = new HashMap<>();
        user_1.put(username, password);
        userDB.put(username, user_1);
        userDAO.setTestUserDB(userDB);
    }

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData(username, password);
        loginInteractor.execute(inputData);

        assertNull(testPresenter.getErrorMessage());
        assertEquals(username, sessionDataAccess.getCurrentUserId());
        assertFalse(testPresenter.getSuccessData().getLoginStatus());
    }

    @Test
    void testUserDoesNotExist() {
        LoginInputData inputData = new LoginInputData("Megumi", "Demon dog");
        loginInteractor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("User does not exist", testPresenter.getErrorMessage());
    }

    @Test
    void testEmptyUsername() {
        LoginInputData inputData = new LoginInputData("", password);
        loginInteractor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Username cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testEmptyPassword() {
        LoginInputData inputData = new LoginInputData(username, "");
        loginInteractor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Password cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testPasswordMismatch() {
        LoginInputData inputData = new LoginInputData(username, "Kenjaku");
        loginInteractor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Password incorrect", testPresenter.getErrorMessage());
    }
}
