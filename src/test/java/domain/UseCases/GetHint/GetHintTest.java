package domain.UseCases.GetHint;

import application.use_cases.Hint.GetHint.*;
import domain.entities.Hint.CommonHint;
import domain.entities.Hint.Hint;
import domain.entities.User.CommonUser;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GetHintTest {

    private TestHintPresenter testPresenter;
    private TestHintDataAccess testHintDAO;
    private TestUserDataAccess testUserDAO;
    private GetHintInteractor interactor;

    private String validObjectId;
    private String validUserId;
    private CommonUser runtimeUser;
    private CommonHint sampleHint;

    private static class TestHintPresenter implements GetHintOutputBoundary {
        private GetHintOutputData successData;
        private String message;

        @Override
        public void prepareSuccessView(GetHintOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.message = error;
        }

        public GetHintOutputData getSuccessData() { return successData; }
        public String getMessage() { return message; }
    }

    private static class TestHintDataAccess implements GetHintDataAccessInterface {
        private final Map<String, Hint> hints = new HashMap<>();

        public void addHint(String objectId, Hint hint) {
            hints.put(objectId, hint);
        }

        @Override
        public Boolean existByObjectID(String id) {
            return hints.containsKey(id);
        }

        @Override
        public Hint getHintForObjectID(String id) {
            return hints.get(id);
        }
    }

    private static class TestUserDataAccess implements GetHintUserDataAccessInterface {
        private User currentUser;

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }
    }

    @BeforeEach
    void setup() {
        testPresenter = new TestHintPresenter();
        testHintDAO = new TestHintDataAccess();
        testUserDAO = new TestUserDataAccess();
        interactor = new GetHintInteractor(testPresenter, testHintDAO, testUserDAO);

        this.validObjectId = "magic_want";
        this.validUserId = "harry_potter";

        this.runtimeUser = new CommonUser(validUserId, "password123");
        testUserDAO.setCurrentUser(runtimeUser);
        runtimeUser.setActiveGameMode("StoryMode");

        List<String> hintMessages = new ArrayList<>();
        hintMessages.add("First basic hint message.");
        hintMessages.add("Second advanced hint message.");
        this.sampleHint = new CommonHint(validObjectId, "/images/hint.png", hintMessages);
        testHintDAO.addHint(validObjectId, sampleHint);
    }

    @Test
    void successTest() {
        HashMap<String, Integer> hintsWatched = new HashMap<>();
        hintsWatched.put(validObjectId, 1);
        runtimeUser.setStoryModeHintsWatched(hintsWatched);

        GetHintInputData inputData = new GetHintInputData(validObjectId, validUserId);

        interactor.execute(inputData);

        assertNull(testPresenter.getMessage());

        GetHintOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData, "Output payload should be captured.");

        assertEquals("Second advanced hint message.", outputData.getMessage());
    }

    @Test
    void testObjectIsNotHintObject() {
        GetHintInputData inputData = new GetHintInputData("invisibility_cloak", validUserId);

        interactor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals( "No hints available", testPresenter.getMessage());
    }

    @Test
    void testCurrentUserMismatch() {
        GetHintInputData inputData = new GetHintInputData(validObjectId, "batman");

        interactor.execute(inputData);

        assertNull(testPresenter.getSuccessData());
        assertEquals( "You are not the current user", testPresenter.getMessage());
    }
}
