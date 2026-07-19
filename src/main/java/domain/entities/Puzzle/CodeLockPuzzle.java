package domain.entities.Puzzle;

public class CodeLockPuzzle implements Puzzle {
    private String id;
    private boolean isSolved;

    private String answer;
    private String hint;

    private String rewardItemId;
    private String unlockedRoomId;

    public CodeLockPuzzle(String id, String answer, String hint, String rewardItemId, String unlockedRoomId) {
        this.id = id;
        this.isSolved = false;
        this.answer = answer;
        this.hint = hint;
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
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getHint() {
        return hint;
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
