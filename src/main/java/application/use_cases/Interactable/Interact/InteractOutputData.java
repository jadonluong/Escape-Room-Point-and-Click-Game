package application.use_cases.Interactable.Interact;

public class InteractOutputData {
    private String successMessage;
    private String rewardItemId;

    public InteractOutputData(String successMessage, String rewardItemId) {
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
    public String getRewardItemId() {
        return rewardItemId;
    }
}