package application.use_cases.puzzle.enter_exit;

public class EnterExitInputData {
    private String puzzleId;
    private String interactableId;

    public EnterExitInputData(String puzzleId, String interactableId) {
        this.puzzleId = puzzleId;
        this.interactableId = interactableId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
