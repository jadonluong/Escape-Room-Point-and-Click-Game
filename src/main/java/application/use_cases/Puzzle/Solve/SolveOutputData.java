package application.use_cases.Puzzle.Solve;

public class SolveOutputData {
    private String successMessage;
    private String rewardItemId;
    private String rewardItemName;
    private String interactableId;

    public SolveOutputData(String successMessage, String rewardItemId, String rewardItemName, String interactableId) {
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
        this.rewardItemName = rewardItemName;
        this.interactableId = interactableId;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getRewardItemId() {
        return rewardItemId;
    }

    public String getRewardItemName() {
        return rewardItemName;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
