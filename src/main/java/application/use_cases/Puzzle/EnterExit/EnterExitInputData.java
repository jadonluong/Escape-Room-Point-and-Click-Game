package application.use_cases.Puzzle.EnterExit;

public class EnterExitInputData {
    private String userId;
    private String puzzleId;
    private String interactableId;

    public EnterExitInputData(String userId, String puzzleId, String interactableId) {
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.interactableId = interactableId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
