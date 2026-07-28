package application.use_cases.Puzzle.EnterExit;

public class EnterExitInputData {
    private String userId;
    private String puzzleId;

    public EnterExitInputData(String userId, String puzzleId) {
        this.userId = userId;
        this.puzzleId = puzzleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }
}
