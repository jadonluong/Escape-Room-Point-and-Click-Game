package domain.entities.Puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnagramPuzzleTest {

    private AnagramPuzzle puzzle;
    private final String ID = "creative_id";
    private final String SCRAMBLED = "PDAISRE";
    private final List<String> ANSWERS = new ArrayList<>();
    private final String SUCCESS_MESSAGE = "YAY!";
    private final String REWARD_ITEM = "reward_item";
    private final String UNLOCKED_ROOM = "unlocked_room";

    @BeforeEach
    void setup() {
        ANSWERS.add("DESPAIR");
        ANSWERS.add("DIAPERS");
        ANSWERS.add("ASPIRED");
        this.puzzle = new AnagramPuzzle(
                ID,
                SCRAMBLED,
                ANSWERS,
                SUCCESS_MESSAGE,
                REWARD_ITEM,
                UNLOCKED_ROOM
        );
    }

    @Test
    void anagramPuzzleTestNormal() {
        assertEquals(ID, puzzle.getId());
        assertEquals(SCRAMBLED, puzzle.getScrambled());
        assertEquals(ANSWERS, puzzle.getAnswers());
        assertFalse(puzzle.isSolved());
        assertEquals(SUCCESS_MESSAGE, puzzle.getSuccessMessage());
        assertEquals(REWARD_ITEM, puzzle.getRewardItemId());
        assertEquals(UNLOCKED_ROOM, puzzle.getUnlockedRoomId());

        // Pre-written
        assertNotNull(puzzle.getDescription());
        assertNotNull(puzzle.getHint());
    }

    @Test
    void anagramPuzzleTestNullAndEmpty() {
        AnagramPuzzle nullPuzzle = new AnagramPuzzle(
                null,
                null,
                new ArrayList<>(),
                null,
                null,
                null
        );
        assertNotNull(nullPuzzle);
        assertNull(nullPuzzle.getId());
        assertNull(nullPuzzle.getScrambled());
        assertTrue(nullPuzzle.getAnswers().isEmpty());
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
    void solveTestAnotherCorrectAnswer() {
        assertTrue(puzzle.solve("DIAPERS"));
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
    void getAnswersTest() {
        assertEquals(ANSWERS, puzzle.getAnswers());
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