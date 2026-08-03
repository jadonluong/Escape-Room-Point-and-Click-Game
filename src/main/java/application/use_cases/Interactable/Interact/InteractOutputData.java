package application.use_cases.Interactable.Interact;

public class InteractOutputData {
    private String successMessage;
    private String rewardItemName; // For InventoryState

    public InteractOutputData(String successMessage, String rewardItemName) {
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