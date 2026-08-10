package application.use_cases.puzzle;

import application.use_cases.puzzle.solve.*;
import domain.entities.item.Item;
import domain.entities.puzzle.*;
import domain.entities.room.Room;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SolveTest {

    private static class TestSolveDataAccess implements SolveDataAccessInterface {
        private final Map<String, Puzzle> puzzles = new HashMap<>();
        private final Map<String, Item> items = new HashMap<>();

        @Override
        public Puzzle getPuzzleById(String puzzleId) {
            return puzzles.get(puzzleId);
        }

        @Override
        public Item getItemById(String itemId) {
            return items.get(itemId);
        }

        public void addPuzzle(Puzzle puzzle) {
            puzzles.put(puzzle.getId(), puzzle);
        }

        public void addItem(Item item) {
            items.put(item.getId(), item);
        }
    }

    private static class TestSolveOutputBoundary implements SolveOutputBoundary {
        public SolveOutputData lastSuccessData;
        public boolean failureCalled = false;
        public String failureMessage = null;

        @Override
        public void prepareSuccessView(SolveOutputData outputData) {
            this.lastSuccessData = outputData;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            this.failureCalled = true;
            this.failureMessage = errorMessage;
        }
    }

    private static class TestSolveUserDataAccess implements SolveUserDataAccessInterface {
        private User currentUser;

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user) {
            this.currentUser = user;
        }
    }

    private static class TestUser implements User {
        private final String username;
        private final String password;
        private final Map<String, Boolean> items = new HashMap<>();
        private String activeGameMode = "StoryMode";
        private String currentRoomId = "room_1";
        public boolean itemSaved = false;
        public String savedItemId = null;

        public TestUser(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public void addItemID(String itemId) {
            items.put(itemId, true);
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isRegistered() {
            return true;
        }

        @Override
        public void setActiveGameMode(String mode) {
            this.activeGameMode = mode;
        }

        @Override
        public String getActiveGameMode() {
            return activeGameMode;
        }

        @Override
        public ArrayList<Item> getItemInventory() {
            return new ArrayList<>();
        }

        @Override
        public void saveItem(Item item) {
            this.itemSaved = true;
            this.savedItemId = item.getId();
        }

        @Override
        public boolean removeItem(Item item) {
            return false;
        }

        @Override
        public void saveSelectedItemID(String itemSelected) {
        }

        @Override
        public String getSelectedItemID() {
            return "";
        }

        @Override
        public boolean hasItemID(String itemId) {
            return items.containsKey(itemId);
        }

        @Override
        public void saveHint(String objectID, int maxHintsAvailable) {
        }

        @Override
        public HashMap<String, Integer> getHintsWatched() {
            return new HashMap<>();
        }

        @Override
        public Map<String, HashMap<String, Integer>> getQuickModeHintsWatched() {
            return new HashMap<>();
        }

        @Override
        public HashMap<String, Integer> getStoryModeHintsWatched() {
            return new HashMap<>();
        }

        @Override
        public ArrayList<Room> getRoomsUnlocked() {
            return new ArrayList<>();
        }

        @Override
        public void unlockRoom(Room room) {
        }

        @Override
        public void saveCurrentRoomID(String roomID) {
            this.currentRoomId = roomID;
        }

        @Override
        public String getCurrentRoomID() {
            return currentRoomId;
        }

        @Override
        public String getStoryModeCurrentRoomID() {
            return "";
        }

        @Override
        public void switchRoom(Room room) {
        }

        @Override
        public void saveInteractable(String interactableId) {
        }

        @Override
        public List<String> getStoryModeInteractables() {
            return List.of();
        }
    }

    private static class TestItem implements Item {
        private final String id;
        private final String name;

        public TestItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public Boolean getCraftable() {
            return false;
        }

        @Override
        public String getImagePath() {
            return "";
        }
    }

    private TestSolveDataAccess dataAccess;
    private TestSolveOutputBoundary outputBoundary;
    private TestSolveUserDataAccess userDataAccess;
    private SolveInteractor interactor;

    private final String PUZZLE_ID = "puzzle_1";
    private final String INTERACTABLE_ID = "interactable_1";
    private final String REWARD_ITEM_ID = "reward_item_1";
    private final String REWARD_ITEM_NAME = "Gold Coin";

    @BeforeEach
    void setUp() {
        dataAccess = new TestSolveDataAccess();
        outputBoundary = new TestSolveOutputBoundary();
        userDataAccess = new TestSolveUserDataAccess();

        TestUser player = new TestUser("test_user", "password");
        userDataAccess.setCurrentUser(player);

        interactor = new SolveInteractor(dataAccess, outputBoundary, userDataAccess);
    }

    private AnagramPuzzle createAnagramPuzzle(String id) {
        return new AnagramPuzzle(
                id,
                "PDAISRE",
                "DESPAIR",
                "A feeling of hopelessness",
                "You unscrambled the word!",
                null,
                null
        );
    }

    private AnagramPuzzle createAnagramPuzzleWithReward(String id) {
        return new AnagramPuzzle(
                id,
                "PDAISRE",
                "DESPAIR",
                "A feeling of hopelessness",
                "You unscrambled the word! You found a coin!",
                REWARD_ITEM_ID,
                "room_2"
        );
    }

    private CryptogramPuzzle createCryptogramPuzzle(String id) {
        Map<String, String> cipher = new HashMap<>();
        cipher.put("A", "H");
        cipher.put("B", "L");
        cipher.put("C", "E");

        return new CryptogramPuzzle(
                id,
                "SDVF DX JRHZ",
                "LIFE IS WHAT",
                cipher,
                null,
                "You decoded the cryptogram!",
                null,
                null
        );
    }

    private CodeLockPuzzle createCodeLockPuzzle(String id) {
        return new CodeLockPuzzle(
                id,
                "Enter the 4-digit code",
                "1234",
                "The code is on the wall",
                "The safe opens!",
                null,
                null
        );
    }

    @Test
    void testSolve_AnagramPuzzle_success() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "DESPAIR", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("You unscrambled the word!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastSuccessData.getInteractableId());
        assertNull(outputBoundary.lastSuccessData.getRewardItemId());
        assertNull(outputBoundary.lastSuccessData.getRewardItemName());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_AnagramPuzzle_caseInsensitive_success() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "despair", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("You unscrambled the word!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_AnagramPuzzle_withSpaces_success() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "  DESPAIR  ", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_AnagramPuzzle_wrongAnswer_failure() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "WRONG", INTERACTABLE_ID));

        assertFalse(puzzle.isSolved());
        assertTrue(outputBoundary.failureCalled);
        assertEquals("Your input was incorrect.", outputBoundary.failureMessage);
        assertNull(outputBoundary.lastSuccessData);
    }

    @Test
    void testSolve_AnagramPuzzle_withReward_success() {
        TestItem rewardItem = new TestItem(REWARD_ITEM_ID, REWARD_ITEM_NAME);
        dataAccess.addItem(rewardItem);

        AnagramPuzzle puzzle = createAnagramPuzzleWithReward(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        TestUser player = (TestUser) userDataAccess.getCurrentUser();

        interactor.solve(new SolveInputData(PUZZLE_ID, "DESPAIR", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("You unscrambled the word! You found a coin!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertEquals(REWARD_ITEM_ID, outputBoundary.lastSuccessData.getRewardItemId());
        assertEquals(REWARD_ITEM_NAME, outputBoundary.lastSuccessData.getRewardItemName());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastSuccessData.getInteractableId());
        assertTrue(player.itemSaved);
        assertEquals(REWARD_ITEM_ID, player.savedItemId);
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_CryptogramPuzzle_success() {
        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "LIFE IS WHAT", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("You decoded the cryptogram!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_CryptogramPuzzle_caseInsensitive_success() {
        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "life is what", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_CryptogramPuzzle_wrongAnswer_failure() {
        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "WRONG ANSWER", INTERACTABLE_ID));

        assertFalse(puzzle.isSolved());
        assertTrue(outputBoundary.failureCalled);
        assertEquals("Your input was incorrect.", outputBoundary.failureMessage);
        assertNull(outputBoundary.lastSuccessData);
    }

    @Test
    void testSolve_CodeLockPuzzle_success() {
        CodeLockPuzzle puzzle = createCodeLockPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "1234", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("The safe opens!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testSolve_CodeLockPuzzle_wrongAnswer_failure() {
        CodeLockPuzzle puzzle = createCodeLockPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "9999", INTERACTABLE_ID));

        assertFalse(puzzle.isSolved());
        assertTrue(outputBoundary.failureCalled);
        assertEquals("Your input was incorrect.", outputBoundary.failureMessage);
        assertNull(outputBoundary.lastSuccessData);
    }

    @Test
    void testSolve_nullPuzzle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            interactor.solve(new SolveInputData("non_existent_id", "answer", INTERACTABLE_ID));
        });
    }

    @Test
    void testSolve_nullAnswer_doesNotThrow() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        assertDoesNotThrow(() -> {
            interactor.solve(new SolveInputData(PUZZLE_ID, null, INTERACTABLE_ID));
        });
        assertFalse(puzzle.isSolved());
        assertTrue(outputBoundary.failureCalled);
        assertEquals("Your input was incorrect.", outputBoundary.failureMessage);
    }

    @Test
    void testSolve_emptyAnswer_failure() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "", INTERACTABLE_ID));

        assertFalse(puzzle.isSolved());
        assertTrue(outputBoundary.failureCalled);
        assertEquals("Your input was incorrect.", outputBoundary.failureMessage);
    }

    @Test
    void testSolve_alreadySolvedPuzzle_callsSetSolvedAgain() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        puzzle.solve("DESPAIR");
        assertTrue(puzzle.isSolved());
        dataAccess.addPuzzle(puzzle);

        interactor.solve(new SolveInputData(PUZZLE_ID, "DESPAIR", INTERACTABLE_ID));

        assertTrue(puzzle.isSolved());
        assertNotNull(outputBoundary.lastSuccessData);
        assertEquals("You unscrambled the word!", outputBoundary.lastSuccessData.getSuccessMessage());
        assertFalse(outputBoundary.failureCalled);
    }
}