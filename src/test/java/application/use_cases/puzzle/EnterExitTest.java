package application.use_cases.puzzle;

import application.use_cases.puzzle.enter_exit.*;
import domain.entities.item.Item;
import domain.entities.puzzle.*;
import domain.entities.room.Room;
import domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnterExitTest {

    private static class TestEnterExitDataAccess implements EnterExitDataAccessInterface {
        private final Map<String, Puzzle> puzzles = new HashMap<>();

        @Override
        public Puzzle getPuzzleById(String puzzleId) {
            return puzzles.get(puzzleId);
        }

        public void addPuzzle(Puzzle puzzle) {
            puzzles.put(puzzle.getId(), puzzle);
        }
    }

    private static class TestEnterExitOutputBoundary implements EnterExitOutputBoundary {
        public EnterExitOutputData lastOutputData;
        public boolean exitCalled = false;
        public boolean failureCalled = false;
        public String failureMessage = null;

        @Override
        public void prepareEnterView(EnterExitOutputData outputData) {
            this.lastOutputData = outputData;
        }

        @Override
        public void prepareExitView() {
            this.exitCalled = true;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            this.failureCalled = true;
            this.failureMessage = errorMessage;
        }
    }

    private static class TestEnterExitUserDataAccess implements EnterExitUserDataAccessInterface {
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
    }

    private TestEnterExitDataAccess dataAccess;
    private TestEnterExitOutputBoundary outputBoundary;
    private TestEnterExitUserDataAccess userDataAccess;
    private EnterExitInteractor interactor;

    private final String PUZZLE_ID = "puzzle_1";
    private final String INTERACTABLE_ID = "interactable_1";
    private final String CIPHER_KEY_ID = "cipher_key_1";

    @BeforeEach
    void setUp() {
        dataAccess = new TestEnterExitDataAccess();
        outputBoundary = new TestEnterExitOutputBoundary();
        userDataAccess = new TestEnterExitUserDataAccess();

        TestUser player = new TestUser("test_user", "password");
        player.addItemID(CIPHER_KEY_ID);
        userDataAccess.setCurrentUser(player);

        interactor = new EnterExitInteractor(dataAccess, outputBoundary, userDataAccess);
    }

    private AnagramPuzzle createAnagramPuzzle(String id) {
        return new AnagramPuzzle(
                id,
                "PDAISRE",
                "DESPAIR",
                "A feeling of hopelessness",
                "You unscrambled the word!",
                "coin_id",
                "room_2"
        );
    }

    private CryptogramPuzzle createCryptogramPuzzle(String id, String cipherKeyId) {
        Map<String, String> cipher = new HashMap<>();
        cipher.put("A", "H");
        cipher.put("B", "L");
        cipher.put("C", "E");

        return new CryptogramPuzzle(
                id,
                "SDVF DX JRHZ",
                "LIFE IS WHAT",
                cipher,
                cipherKeyId,
                "You decoded the cryptogram!",
                "golden_key",
                "room_3"
        );
    }

    private CodeLockPuzzle createCodeLockPuzzle(String id) {
        return new CodeLockPuzzle(
                id,
                "Enter the 4-digit code",
                "1234",
                "The code is on the wall",
                "The safe opens!",
                "key_id",
                "room_4"
        );
    }

    @Test
    void testEnter_AnagramPuzzle_success() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Anagram", outputBoundary.lastOutputData.getPuzzleType());
        assertEquals(puzzle.getDescription(), outputBoundary.lastOutputData.getDescription());
        assertEquals(puzzle.getHint(), outputBoundary.lastOutputData.getHint());
        assertEquals(puzzle.getScrambled(), outputBoundary.lastOutputData.getScrambled());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastOutputData.getInteractableId());
        assertNull(outputBoundary.lastOutputData.getEncrypted());
        assertNull(outputBoundary.lastOutputData.getCipher());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testEnter_CryptogramPuzzle_success() {
        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID, CIPHER_KEY_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Cryptogram", outputBoundary.lastOutputData.getPuzzleType());
        assertEquals(puzzle.getDescription(), outputBoundary.lastOutputData.getDescription());
        assertNull(outputBoundary.lastOutputData.getHint());
        assertEquals(puzzle.getEncrypted(), outputBoundary.lastOutputData.getEncrypted());
        assertEquals(puzzle.getCipher(), outputBoundary.lastOutputData.getCipher());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastOutputData.getInteractableId());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testEnter_CryptogramPuzzle_missingCipherKey_failure() {
        TestUser playerWithoutKey = new TestUser("test_user", "password");
        userDataAccess.setCurrentUser(playerWithoutKey);

        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID, CIPHER_KEY_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));

        assertTrue(outputBoundary.failureCalled);
        assertEquals("You need a cipher key to decode this!", outputBoundary.failureMessage);
        assertNull(outputBoundary.lastOutputData);
    }

    @Test
    void testEnter_CryptogramPuzzle_nullCipherKeyId_success() {
        CryptogramPuzzle puzzle = createCryptogramPuzzle(PUZZLE_ID, null);
        dataAccess.addPuzzle(puzzle);

        interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals("Cryptogram", outputBoundary.lastOutputData.getPuzzleType());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testEnter_CodeLockPuzzle_success() {
        CodeLockPuzzle puzzle = createCodeLockPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("CodeLock", outputBoundary.lastOutputData.getPuzzleType());
        assertEquals(puzzle.getDescription(), outputBoundary.lastOutputData.getDescription());
        assertEquals(puzzle.getHint(), outputBoundary.lastOutputData.getHint());
        assertNull(outputBoundary.lastOutputData.getScrambled());
        assertNull(outputBoundary.lastOutputData.getEncrypted());
        assertNull(outputBoundary.lastOutputData.getCipher());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastOutputData.getInteractableId());
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testEnter_nullPuzzle_returnsNull() {
        interactor.enter(new EnterExitInputData("non_existent_id", INTERACTABLE_ID));

        assertNull(outputBoundary.lastOutputData);
        assertFalse(outputBoundary.failureCalled);
    }

    @Test
    void testEnter_withNullUser_doesNotThrow() {
        userDataAccess.setCurrentUser(null);

        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        assertDoesNotThrow(() -> {
            interactor.enter(new EnterExitInputData(PUZZLE_ID, INTERACTABLE_ID));
        });
    }

    @Test
    void testEnter_withNullInteractableId_doesNotThrow() {
        AnagramPuzzle puzzle = createAnagramPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        assertDoesNotThrow(() -> {
            interactor.enter(new EnterExitInputData(PUZZLE_ID, null));
        });
    }

    @Test
    void testExit() {
        interactor.exit();

        assertTrue(outputBoundary.exitCalled);
    }

    @Test
    void testExit_multipleCalls_worksEachTime() {
        interactor.exit();
        assertTrue(outputBoundary.exitCalled);

        outputBoundary.exitCalled = false;
        interactor.exit();
        assertTrue(outputBoundary.exitCalled);
    }
}