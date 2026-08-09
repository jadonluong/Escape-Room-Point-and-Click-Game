package domain.entities.Puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnagramPuzzleTest {

    private AnagramPuzzle puzzle;
    private final String ID = "creative_id";
    private final String SCRAMBLED = "PDAISRE";
    private final String ANSWER = "DESPAIR";
    private final String HINT = "Utter loss of hope.";
    private final String SUCCESS_MESSAGE = "YAY!";
    private final String REWARD_ITEM = "reward_item";
    private final String UNLOCKED_ROOM = "unlocked_room";

    @BeforeEach
    void setup() {
        this.puzzle = new AnagramPuzzle(
                ID,
                SCRAMBLED,
                ANSWER,
                HINT,
                SUCCESS_MESSAGE,
                REWARD_ITEM,
                UNLOCKED_ROOM
        );
    }

    @Test
    void anagramPuzzleTestNormal() {
        assertEquals(ID, puzzle.getId());
        assertEquals(SCRAMBLED, puzzle.getScrambled());
        assertFalse(puzzle.isSolved());
        assertEquals(SUCCESS_MESSAGE, puzzle.getSuccessMessage());
        assertEquals(REWARD_ITEM, puzzle.getRewardItemId());
        assertEquals(UNLOCKED_ROOM, puzzle.getUnlockedRoomId());

        // Pre-written
        assertNotNull(puzzle.getDescription());
        assertNotNull(puzzle.getHint());
    }

    @Test
    void anagramPuzzleTestNull() {
        AnagramPuzzle nullPuzzle = new AnagramPuzzle(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertNotNull(nullPuzzle);
        assertNull(nullPuzzle.getId());
        assertNull(nullPuzzle.getScrambled());
        assertNull(nullPuzzle.getSuccessMessage());
        assertNull(nullPuzzle.getRewardItemId());
        assertNull(nullPuzzle.getUnlockedRoomId());
    }

    @Test
    void solveTestCorrectAnswer() {
        assertTrue(puzzle.solve("DESPAIR"));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerCaseInsensitive() {
        assertTrue(puzzle.solve("despair"));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerWithSpaces() {
        assertTrue(puzzle.solve("  DESPAIR  "));
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
    void getScrambledTest() {
        assertEquals(SCRAMBLED, puzzle.getScrambled());
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

    @Test
    void getHintTest() {
        assertNotNull(puzzle.getHint());
    }
}