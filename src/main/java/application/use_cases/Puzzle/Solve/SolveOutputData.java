package application.use_cases.Puzzle.Solve;

public class SolveOutputData {
    private String successMessage;
    private String rewardItemId;
    private String rewardItemName;

    public SolveOutputData(String successMessage, String rewardItemId, String rewardItemName) {
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
        this.rewardItemName = rewardItemName;
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
}
