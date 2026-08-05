package domain.entities.Puzzle;

import java.util.List;

public class AnagramPuzzle implements Puzzle {
    private String id;
    private boolean isSolved;
    private String description;

    private String scrambled;
    private List<String> answers;
    private String hint;

    private String successMessage;
    private String rewardItemId;
    private String unlockedRoomId;

    public AnagramPuzzle(String id, String scrambled, List<String> answers, String successMessage, String rewardItemId,
                         String unlockedRoomId) {
        this.id = id;
        this.isSolved = false;
        this.description = "Unscramble the letters to make a word!";
        this.scrambled = scrambled;
        this.answers = answers;
        this.hint = "You can do it!";
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

    public List<String> getAnswers() {
        return answers;
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
        if (playerAnswer != null) {
            for (String answer : answers) {
                if (answer.equalsIgnoreCase(playerAnswer.trim())) {
                    isSolved = true;
                    return true;
                }
            }
        }
        return false;
    }
}
