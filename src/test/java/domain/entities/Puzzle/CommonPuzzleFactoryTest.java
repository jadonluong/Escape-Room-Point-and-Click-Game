package domain.entities.Puzzle;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommonPuzzleFactoryTest {

    private CommonPuzzleFactory factory = new CommonPuzzleFactory();

    @Test
    void createAnagramTestNormal() {
        String id = "creative_id";
        String scrambled = "PDAISRE";
        String answer = "DESPAIR";
        String hint = "Utter loss of hope.";
        String successMessage = "YAY!";
        String rewardItem = "reward_item";
        String unlockedRoom = "unlocked_room";

        AnagramPuzzle puzzle = factory.createAnagram(
                id,
                scrambled,
                answer,
                hint,
                successMessage,
                rewardItem,
                unlockedRoom
        );

        assertNotNull(puzzle);
        assertEquals(id, puzzle.getId());
        assertEquals(scrambled, puzzle.getScrambled());
        assertEquals(successMessage, puzzle.getSuccessMessage());
        assertEquals(rewardItem, puzzle.getRewardItemId());
        assertEquals(unlockedRoom, puzzle.getUnlockedRoomId());
        assertFalse(puzzle.isSolved());

        // Pre-written
        assertNotNull(puzzle.getDescription());
        assertNotNull(puzzle.getHint());
    }

    @Test
    void createCryptogramTestNormal() {
        String id = "creative_id";
        String encrypted = "MKZ MLVLN";
        String answer = "SIX SEVEN";
        Map<String, String> cipher = new HashMap<>();
        cipher.put("A", "A");
        cipher.put("B", "B");
        cipher.put("C", "C");
        cipher.put("D", "D");
        cipher.put("E", "L");
        cipher.put("F", "F");
        cipher.put("G", "G");
        cipher.put("H", "H");
        cipher.put("I", "K");
        cipher.put("J", "J");
        cipher.put("K", "S");
        cipher.put("L", "X");
        cipher.put("M", "E");
        cipher.put("N", "N");
        cipher.put("O", "O");
        cipher.put("P", "P");
        cipher.put("Q", "Q");
        cipher.put("R", "R");
        cipher.put("S", "M");
        cipher.put("T", "T");
        cipher.put("U", "U");
        cipher.put("V", "V");
        cipher.put("W", "W");
        cipher.put("X", "Z");
        cipher.put("Y", "Y");
        cipher.put("Z", "I");
        String cipherKeyId = "cipher_key";
        String successMessage = "YAY!";
        String rewardItem = "reward_item";
        String unlockedRoom = "unlocked_room";

        CryptogramPuzzle puzzle = factory.createCryptogram(
                id,
                encrypted,
                answer,
                cipher,
                cipherKeyId,
                successMessage,
                rewardItem,
                unlockedRoom
        );

        assertNotNull(puzzle);
        assertEquals(id, puzzle.getId());
        assertEquals(encrypted, puzzle.getEncrypted());
        assertEquals(cipher, puzzle.getCipher());
        assertEquals(cipherKeyId, puzzle.getCipherKeyId());
        assertEquals(successMessage, puzzle.getSuccessMessage());
        assertEquals(rewardItem, puzzle.getRewardItemId());
        assertEquals(unlockedRoom, puzzle.getUnlockedRoomId());
        assertFalse(puzzle.isSolved());

        // Pre-written
        assertNotNull(puzzle.getDescription());
    }

    @Test
    void createCodeLockTestNormal() {
        String id = "creative_id";
        String description = "A 4-digit number code is required.";
        String answer = "6767";
        String hint = "The answer is somewhere on the wall...";
        String successMessage = "YAY!";
        String rewardItem = "reward_item";
        String unlockedRoom = "unlocked_room";

        CodeLockPuzzle puzzle = factory.createCodeLock(
                id,
                description,
                answer,
                hint,
                successMessage,
                rewardItem,
                unlockedRoom
        );

        assertNotNull(puzzle);
        assertEquals(id, puzzle.getId());
        assertEquals(description, puzzle.getDescription());
        assertEquals(hint, puzzle.getHint());
        assertEquals(successMessage, puzzle.getSuccessMessage());
        assertEquals(rewardItem, puzzle.getRewardItemId());
        assertEquals(unlockedRoom, puzzle.getUnlockedRoomId());
        assertFalse(puzzle.isSolved());
    }

    @Test
    void createAnagramTestNull() {
        AnagramPuzzle puzzle = factory.createAnagram(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertNotNull(puzzle);
        assertNull(puzzle.getId());
        assertNull(puzzle.getScrambled());
        assertNull(puzzle.getSuccessMessage());
        assertNull(puzzle.getRewardItemId());
        assertNull(puzzle.getUnlockedRoomId());
    }

    @Test
    void createCryptogramTestNullAndEmpty() {
        CryptogramPuzzle puzzle = factory.createCryptogram(
                null,
                null,
                null,
                new HashMap<>(),
                null,
                null,
                null,
                null
        );
        assertNotNull(puzzle);
        assertNull(puzzle.getId());
        assertNull(puzzle.getEncrypted());
        assertTrue(puzzle.getCipher().isEmpty());
        assertNull(puzzle.getCipherKeyId());
        assertNull(puzzle.getSuccessMessage());
        assertNull(puzzle.getRewardItemId());
        assertNull(puzzle.getUnlockedRoomId());
    }

    @Test
    void createCodeLockTestNull() {
        CodeLockPuzzle puzzle = factory.createCodeLock(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertNotNull(puzzle);
        assertNull(puzzle.getId());
        assertNull(puzzle.getDescription());
        assertNull(puzzle.getHint());
        assertNull(puzzle.getSuccessMessage());
        assertNull(puzzle.getRewardItemId());
        assertNull(puzzle.getUnlockedRoomId());
    }
}