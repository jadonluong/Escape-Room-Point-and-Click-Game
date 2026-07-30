package application.use_cases.Puzzle.Solve;

public class SolveOutputData {
    private String successMessage;

    public SolveOutputData(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
