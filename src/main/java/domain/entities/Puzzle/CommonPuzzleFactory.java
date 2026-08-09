package domain.entities.Puzzle;

import java.util.Map;

public class CommonPuzzleFactory implements PuzzleFactory {
    @Override
    public AnagramPuzzle createAnagram(String id, String scrambled, String answer, String hint, String successMessage,
                                       String rewardItemId, String unlockedRoomId) {
        return new AnagramPuzzle(id, scrambled, answer, hint, successMessage, rewardItemId, unlockedRoomId);
    }

    @Override
    public CryptogramPuzzle createCryptogram(String id, String encrypted, String answer, Map<String, String> cipher,
                                             String cipherKeyId, String successMessage, String rewardItemId,
                                             String unlockedRoomId) {
        return new CryptogramPuzzle(id, encrypted, answer, cipher, cipherKeyId, successMessage, rewardItemId,
                unlockedRoomId);
    }

    @Override
    public CodeLockPuzzle createCodeLock(String id, String description, String answer, String hint,
                                         String successMessage, String rewardItemId, String unlockedRoomId) {
        return new CodeLockPuzzle(id, description, answer, hint, successMessage, rewardItemId, unlockedRoomId);
    }
}
