package domain.entities.puzzle;

import java.util.Map;

public interface PuzzleFactory {
    AnagramPuzzle createAnagram(String id, String scrambled, String answer, String hint, String successMessage,
                                String rewardItemId, String unlockedRoomId);

    CryptogramPuzzle createCryptogram(String id, String encrypted, String answer, Map<String, String> cipher,
                                      String cipherKeyId, String successMessage, String rewardItemId,
                                      String unlockedRoomId);

    CodeLockPuzzle createCodeLock(String id, String description, String answer, String hint, String successMessage,
                                  String rewardItemId, String unlockedRoomId);
}
