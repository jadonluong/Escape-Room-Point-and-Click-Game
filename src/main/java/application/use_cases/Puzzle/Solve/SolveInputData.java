package application.use_cases.Puzzle.Solve;

public class SolveInputData {
    private String puzzleId;
    private String playerAnswer;

    public SolveInputData(String puzzleId, String playerAnswer) {
        this.puzzleId = puzzleId;
        this.playerAnswer = playerAnswer;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }
}
