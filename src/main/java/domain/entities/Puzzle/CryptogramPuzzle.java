package domain.entities.Puzzle;

import java.util.Map;

public class CryptogramPuzzle implements Puzzle{
    private String id;
    private boolean isSolved;

    private String encrypted;
    private String answer;
    private String hint;
    private Map<String, String> cipher;

    private String rewardItemId;
    private String unlockedRoomId;

    public CryptogramPuzzle(String id, String encrypted, String answer, String hint, Map<String, String> cipher,
                            String rewardItemId, String unlockedRoomId) {
        this.id = id;
        this.isSolved = false;
        this.encrypted = encrypted;
        this.answer = answer;
        this.hint = hint;
        this.cipher = cipher;
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

    public String getEncrypted() {
        return encrypted;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getHint() {
        return hint;
    }

    public Map<String, String> getCipher() {
        return cipher;
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
