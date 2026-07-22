package application.use_cases.Puzzle.Solve;

public class SolveOutputData {
    private String userId;
    private String interactableId;
    private String successMessage;

    public SolveOutputData(String userId, String interactableId, String successMessage) {
        this.userId = userId;
        this.interactableId = interactableId;
        this.successMessage = successMessage;
    }

    public String getUserId() {
        return userId;
    }

    public String getInteractableId() {
        return interactableId;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
