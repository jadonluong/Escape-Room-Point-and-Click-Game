package application.use_cases.user;

import application.use_cases.user.signup.*;
import domain.entities.user.CommonUser;
import domain.entities.user.CommonUserFactory;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupTest {
    private TestSignupPresenter testPresenter;
    private TestUserDataAccess testUserDAO;
    private SignupInteractor interactor;

    private String username;
    private String password;
    private String repeatedPassword;

    private static class TestSignupPresenter implements SignupOutputBoundary {
        private SignupOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void switchToLoginView() {
        }

        public SignupOutputData getSuccessData() {
            return successData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    private static class TestUserDataAccess implements SignupUserDataAccessInterface {
        private final Map<String, HashMap<String, String>> testUserDB = new HashMap<>();


        @Override
        public boolean existsByName(String username) {
            return testUserDB.containsKey(username);
        }

        @Override
        public void save(User user) {
            CommonUser commonUser = (CommonUser) user;
            String username = commonUser.getUsername();
            String password = commonUser.getPassword();
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put(username, password);
            testUserDB.put(username, userInfo);
        }

        public Integer getUserDatabaseSize() {
            return testUserDB.size();
        }
    }

    private static class TestProfanityCheck implements ProfanityCheck {

        @Override
        public boolean hasProfanity(String username) {
            return "has_profanity".equals(username);
        }
    }

    private static class TestCommonUserFactory implements CommonUserFactory {

        @Override
        public User createCommonUser(String username, String password) {
            return new CommonUser(username, password);
        }
    }

    @BeforeEach
    void setup() {
        testPresenter = new TestSignupPresenter();
        testUserDAO = new TestUserDataAccess();
        TestCommonUserFactory commonUserFactory = new TestCommonUserFactory();
        TestProfanityCheck profanityCheck = new TestProfanityCheck();
        interactor = new SignupInteractor(testUserDAO, testPresenter, commonUserFactory, profanityCheck);

        this.username = "Yuta";
        this.password = "Rika";
        this.repeatedPassword = "Rika";

        testUserDAO.save(new CommonUser("Satoru Gojo", "Daifuku"));
        testUserDAO.save(new CommonUser("Suguru Geto", "Monkeys"));
    }

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData(username, password, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getErrorMessage());

        assertEquals(3, testUserDAO.getUserDatabaseSize());

        SignupOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData, "Output payload should be captured.");

        assertEquals(username, outputData.getUsername());
        assertFalse(outputData.isUseCaseFailed());
    }

    @Test
    void testEmptyUsername() {
        SignupInputData inputData = new SignupInputData("", password, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Username cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testWhitespaceOnlyUsername() {
        SignupInputData inputData = new SignupInputData("  ", password, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Username cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testNullUsername() {
        SignupInputData inputData = new SignupInputData(null, password, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Username cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testEmptyPassword() {
        SignupInputData inputData = new SignupInputData(username, "", repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Password cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testWhiteSpaceOnlyPassword() {
        SignupInputData inputData = new SignupInputData(username, " ", repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Password cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testNullPassword() {
        SignupInputData inputData = new SignupInputData(username, null, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Password cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testRepeatedPasswordMismatch() {
        SignupInputData inputData = new SignupInputData(username, password, "Special grade");
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Passwords don't match.", testPresenter.getErrorMessage());
    }

    @Test
    void testUserExists() {
        SignupInputData inputData = new SignupInputData("Satoru Gojo", "Nani", "Nani");
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("user already exists.", testPresenter.getErrorMessage());
    }

    @Test
    void testHasProfanityInUsername() {
        SignupInputData inputData = new SignupInputData("has_profanity", password, repeatedPassword);
        interactor.executeSignup(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals("Username contains inappropriate language.", testPresenter.getErrorMessage());
    }
}
