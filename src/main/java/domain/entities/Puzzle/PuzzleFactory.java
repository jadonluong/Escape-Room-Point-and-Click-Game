package domain.entities.Puzzle;

import java.util.Map;

public interface PuzzleFactory {
    AnagramPuzzle createAnagram(String id, String description, String scrambled, String answer, String hint,
                                String successMessage, String rewardItemId, String unlockedRoomId);

    CryptogramPuzzle createCryptogram(String id, String description, String encrypted, String answer, String hint,
                                      Map<String, String> cipher, String cipherKeyId, String successMessage,
                                      String rewardItemId, String unlockedRoomId);

    CodeLockPuzzle createCodeLock(String id, String description, String answer, String hint, String successMessage,
                                  String rewardItemId, String unlockedRoomId);
}
