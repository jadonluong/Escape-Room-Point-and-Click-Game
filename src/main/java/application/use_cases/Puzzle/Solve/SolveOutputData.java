package application.use_cases.Puzzle.Solve;

public class SolveOutputData {
    private String successMessage;
    private String rewardItemName;

    public SolveOutputData(String successMessage, String rewardItemName) {
        this.successMessage = successMessage;
        this.rewardItemName = rewardItemName;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getRewardItemName() {
        return rewardItemName;
    }
}
