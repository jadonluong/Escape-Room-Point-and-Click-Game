package domain.entities.puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeLockPuzzleTest {

    private CodeLockPuzzle puzzle;
    private final String ID = "creative_id";
    private final String DESCRIPTION = "A 4-digit number code is required.";
    private final String ANSWER = "6767";
    private final String HINT = "The answer is somewhere on the wall...";
    private final String SUCCESS_MESSAGE = "YAY!";
    private final String REWARD_ITEM = "reward_item";
    private final String UNLOCKED_ROOM = "unlocked_room";

    @BeforeEach
    void setup() {
        this.puzzle = new CodeLockPuzzle(
                ID,
                DESCRIPTION,
                ANSWER,
                HINT,
                SUCCESS_MESSAGE,
                REWARD_ITEM,
                UNLOCKED_ROOM
        );
    }

    @Test
    void codeLockPuzzleTestNormal() {
        assertEquals(ID, puzzle.getId());
        assertEquals(DESCRIPTION, puzzle.getDescription());
        assertEquals(HINT, puzzle.getHint());
        assertFalse(puzzle.isSolved());
        assertEquals(SUCCESS_MESSAGE, puzzle.getSuccessMessage());
        assertEquals(REWARD_ITEM, puzzle.getRewardItemId());
        assertEquals(UNLOCKED_ROOM, puzzle.getUnlockedRoomId());
    }

    @Test
    void codeLockPuzzleTestNull() {
        CodeLockPuzzle nullPuzzle = new CodeLockPuzzle(
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
        assertNull(nullPuzzle.getDescription());
        assertNull(nullPuzzle.getHint());
        assertNull(nullPuzzle.getSuccessMessage());
        assertNull(nullPuzzle.getRewardItemId());
        assertNull(nullPuzzle.getUnlockedRoomId());
    }

    @Test
    void solveTestCorrectAnswer() {
        assertTrue(puzzle.solve(ANSWER));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerCaseInsensitive() {
        assertTrue(puzzle.solve("6767"));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestCorrectAnswerWithSpaces() {
        assertTrue(puzzle.solve("  6767  "));
        assertTrue(puzzle.isSolved());
    }

    @Test
    void solveTestIncorrectAnswer() {
        assertFalse(puzzle.solve("420"));
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
    void getDescriptionTest() {
        assertEquals(DESCRIPTION, puzzle.getDescription());
    }

    @Test
    void getHintTest() {
        assertEquals(HINT, puzzle.getHint());
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
}