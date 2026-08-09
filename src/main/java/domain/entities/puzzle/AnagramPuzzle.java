package domain.entities.puzzle;

public class AnagramPuzzle implements Puzzle {
    private String id;
    private boolean isSolved;
    private String description;

    private String scrambled;
    private String answer;
    private String hint;

    private String successMessage;
    private String rewardItemId;
    private String unlockedRoomId;

    public AnagramPuzzle(String id, String scrambled, String answer, String hint, String successMessage,
                         String rewardItemId, String unlockedRoomId) {
        this.id = id;
        this.isSolved = false;
        this.description = "Unscramble the letters to make a word!";
        this.scrambled = scrambled;
        this.answer = answer;
        this.hint = hint;
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

    public String getScrambled() {
        return scrambled;
    }

    public String getHint() {
        return hint;
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
        final boolean result;
        if (playerAnswer != null && answer.equalsIgnoreCase(playerAnswer.trim())) {
            isSolved = true;
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }
}
