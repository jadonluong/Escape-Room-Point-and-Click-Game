package application.use_cases.Puzzle.Solve;

public class SolveInputData {
    private String userId;
    private String puzzleId;
    private String playerAnswer;

    public SolveInputData(String userId, String puzzleId, String playerAnswer) {
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.playerAnswer = playerAnswer;
    }

    public String getUserId() {
        return userId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }
}
