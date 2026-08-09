package application.use_cases.puzzle.solve;

public class SolveInputData {
    private String puzzleId;
    private String playerAnswer;
    private String interactableId;

    public SolveInputData(String puzzleId, String playerAnswer, String interactableId) {
        this.puzzleId = puzzleId;
        this.playerAnswer = playerAnswer;
        this.interactableId = interactableId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
