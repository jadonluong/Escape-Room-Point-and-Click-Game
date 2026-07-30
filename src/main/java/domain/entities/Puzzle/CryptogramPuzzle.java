package domain.entities.Puzzle;

import java.util.Map;

public class CryptogramPuzzle implements Puzzle{
    private String id;
    private boolean isSolved;
    private String description;

    private String encrypted;
    private String answer;
    private Map<String, String> cipher;
    private String cipherKeyId;

    private String successMessage;
    private String rewardItemId;
    private String unlockedRoomId;

    public CryptogramPuzzle(String id, String encrypted, String answer, Map<String, String> cipher, String cipherKeyId,
                            String successMessage, String rewardItemId, String unlockedRoomId) {
        this.id = id;
        this.isSolved = false;
        this.description = "Use the cipher to translate the encrypted sentence!";
        this.encrypted = encrypted;
        this.answer = answer;
        this.cipher = cipher;
        this.cipherKeyId = cipherKeyId;
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
        this.unlockedRoomId = unlockedRoomId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isSolved() {
        return isSolved;
    }

    @Override
    public void setSolved(boolean solved) {
        this.isSolved = solved;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getEncrypted() {
        return encrypted;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    public Map<String, String> getCipher() {
        return cipher;
    }

    public String getCipherKeyId() {
        return cipherKeyId;
    }

    @Override
    public String getSuccessMessage() {
        return successMessage;
    }

    @Override
    public String getRewardItemId() {
        return rewardItemId;
    }

    @Override
    public String getUnlockedRoomId() {
        return unlockedRoomId;
    }

    @Override
    public boolean solve(String playerAnswer) {
        if (answer.equalsIgnoreCase(playerAnswer.trim())) {
            isSolved = true;
            return true;
        }
        return false;
    }
}
