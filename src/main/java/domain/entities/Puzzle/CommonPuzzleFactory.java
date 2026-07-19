package domain.entities.Puzzle;

import java.util.Map;

public class CommonPuzzleFactory implements PuzzleFactory {
    @Override
    public AnagramPuzzle createAnagram(String id, String scrambled, String answer, String hint, String rewardItemId,
                                       String unlockedRoomId) {
        return new AnagramPuzzle(id, scrambled, answer, hint, rewardItemId, unlockedRoomId);
    }

    @Override
    public CryptogramPuzzle createCryptogram(String id, String encrypted, String answer, String hint,
                                             Map<String, String> cipher, String rewardItemId, String unlockedRoomId) {
        return new CryptogramPuzzle(id, encrypted, answer, hint, cipher, rewardItemId, unlockedRoomId );
    }

    @Override
    public CodeLockPuzzle createCodeLock(String id, String answer, String hint, String rewardItemId,
                                         String unlockedRoomId) {
        return new CodeLockPuzzle(id, answer, hint, rewardItemId, unlockedRoomId);
    }
}
