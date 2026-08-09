package domain.entities.puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CryptogramPuzzleTest {

    private CryptogramPuzzle puzzle;
    private final String ID = "creative_id";
    private final String ENCRYPTED = "MKZ MLVLN";
    private final String ANSWER = "SIX SEVEN";
    private final Map<String, String> CIPHER = new HashMap<>();
    private final String CIPHER_KEY_ID = "cipher_key";
    private final String SUCCESS_MESSAGE = "YAY!";
    private final String REWARD_ITEM = "reward_item";
    private final String UNLOCKED_ROOM = "unlocked_room";

    @BeforeEach
    void setup() {
        CIPHER.put("A", "A");
        CIPHER.put("B", "B");
        CIPHER.put("C", "C");
        CIPHER.put("D", "D");
        CIPHER.put("E", "L");
        CIPHER.put("F", "F");
        CIPHER.put("G", "G");
        CIPHER.put("H", "H");
        CIPHER.put("I", "K");
        CIPHER.put("J", "J");
        CIPHER.put("K", "S");
        CIPHER.put("L", "X");
        CIPHER.put("M", "E");
        CIPHER.put("N", "N");
        CIPHER.put("O", "O");
        CIPHER.put("P", "P");
        CIPHER.put("Q", "Q");
        CIPHER.put("R", "R");
        CIPHER.put("S", "M");
        CIPHER.put("T", "T");
        CIPHER.put("U", "U");
        CIPHER.put("V", "V");
        CIPHER.put("W", "W");
        CIPHER.put("X", "Z");
        CIPHER.put("Y", "Y");
        CIPHER.put("Z", "I");

        this.puzzle = new CryptogramPuzzle(
                ID,
                ENCRYPTED,
                ANSWER,
                CIPHER,
                CIPHER_KEY_ID,
                SUCCESS_MESSAGE,
                REWARD_ITEM,
                UNLOCKED_ROOM
        );
    }

    @Test
    void cryptogramPuzzleTestNormal() {
        assertEquals(ID, puzzle.getId());
        assertEquals(ENCRYPTED, puzzle.getEncrypted());
        assertEquals(CIPHER, puzzle.getCipher());
        assertEquals(CIPHER_KEY_ID, puzzle.getCipherKeyId());
        assertFalse(puzzle.isSolved());
        assertEquals(SUCCESS_MESSAGE, puzzle.getSuccessMessage());
        assertEquals(REWARD_ITEM, puzzle.getRewardItemId());
        assertEquals(UNLOCKED_ROOM, puzzle.getUnlockedRoomId());

        // Pre-written
        assertNotNull(puzzle.getDescription());
    }

    @Test
    void cryptogramPuzzleTestNullAndEmpty() {
        CryptogramPuzzle nullPuzzle = new CryptogramPuzzle(
                null,
                null,
                null,
                new HashMap<>(),
                null,
                null,
                null,
                null
        );
        assertNotNull(nullPuzzle);
        assertNull(nullPuzzle.getId());
        assertNull(nullPuzzle.getEncrypted());
        assertTrue(nullPuzzle.getCipher().isEmpty());
        assertNull(nullPuzzle.getCipherKeyId());
        assertNull(nullPuzzle.getSuccessMessage());
        assertNull(nullPuzzle.getRewardItemId());
        assertNull(nullPuzzle.getUnlockedRoomId());
    }

    @Test
    void cryptogramPuzzleTestNullCipher() { // Since the free plan doesn't give a cipher...
        CryptogramPuzzle nullCipherPuzzle = new CryptogramPuzzle(
                ID,
                ENCRYPTED,
                ANSWER,
                null,
                CIPHER_KEY_ID,
                SUCCESS_MESSAGE,
                REWARD_ITEM,
                UNLOCKED_ROOM
        );
        assertNull(nullCipherPuzzle.getCipher());
    }

    @Test
    void solveTestCorrectAnswer() {
        assertTrue(puzzle.solve(ANSWER));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerCaseInsensitive() {
        assertTrue(puzzle.solve("six seven"));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerWithSpaces() {
        assertTrue(puzzle.solve("  SIX SEVEN  "));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestIncorrectAnswer() {
        assertFalse(puzzle.solve("WRONG"));
        assertFalse(puzzle.isSolved());
    }

    @Test
    void solveTestNullAnswer() {
        assertFalse(puzzle.solve(null));
        assertFalse(puzzle.isSolved());
    }

    @Test
    void setSolvedTest() {
        puzzle.setSolved(true);
        assertTrue(puzzle.isSolved());

        puzzle.setSolved(false);
        assertFalse(puzzle.isSolved());
    }

    @Test
    void getIdTest() {
        assertEquals(ID, puzzle.getId());
    }

    @Test
    void getEncryptedTest() {
        assertEquals(ENCRYPTED, puzzle.getEncrypted());
    }

    @Test
    void getCipherTest() {
        assertEquals(CIPHER, puzzle.getCipher());
    }

    @Test
    void getCipherKeyIdTest() {
        assertEquals(CIPHER_KEY_ID, puzzle.getCipherKeyId());
    }

    @Test
    void getSuccessMessageTest() {
        assertEquals(SUCCESS_MESSAGE, puzzle.getSuccessMessage());
    }

    @Test
    void getRewardItemIdTest() {
        assertEquals(REWARD_ITEM, puzzle.getRewardItemId());
    }

    @Test
    void getUnlockedRoomIdTest() {
        assertEquals(UNLOCKED_ROOM, puzzle.getUnlockedRoomId());
    }

    @Test
    void getDescriptionTest() {
        assertNotNull(puzzle.getDescription());
    }
}