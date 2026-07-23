package application.use_cases.Puzzle.Solve;

public class SolveInputData {
    private String userId;
    private String puzzleId;
    private String interactableId;
    private String playerAnswer;

    public SolveInputData(String userId, String puzzleId, String interactableId, String playerAnswer) {
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.interactableId = interactableId;
        this.playerAnswer = playerAnswer;
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

    public String getPlayerAnswer() {
        return playerAnswer;
    }
}
