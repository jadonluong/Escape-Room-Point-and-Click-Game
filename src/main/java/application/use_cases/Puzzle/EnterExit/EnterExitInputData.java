package application.use_cases.Puzzle.EnterExit;

public class EnterExitInputData {
    private String puzzleId;

    public EnterExitInputData(String puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }
}
