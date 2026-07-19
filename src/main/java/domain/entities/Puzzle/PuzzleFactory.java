package domain.entities.Puzzle;

import java.util.Map;

public interface PuzzleFactory {
    AnagramPuzzle createAnagram(String id, String scrambled, String answer, String hint, String rewardItemId,
                                String unlockedRoomId);

    CryptogramPuzzle createCryptogram(String id, String encrypted, String answer, String hint,
                                      Map<String, String> cipher, String rewardItemId, String unlockedRoomId);

    CodeLockPuzzle createCodeLock(String id, String answer, String hint, String rewardItemId, String unlockedRoomId);
}
