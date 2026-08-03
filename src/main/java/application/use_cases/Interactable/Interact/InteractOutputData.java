package application.use_cases.Interactable.Interact;

public class InteractOutputData {
    private String successMessage;

    // For InventoryState
    private String rewardItemId;
    private String rewardItemName;
    private String selectedItemId;
    private String selectedItemName;


    public InteractOutputData(String successMessage, String rewardItemId, String rewardItemName, String selectedItemId,
                              String selectedItemName) {
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
        this.rewardItemName = rewardItemName;
        this.selectedItemId = selectedItemId;
        this.selectedItemName = selectedItemName;
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

    public String getSelectedItemId() {
        return selectedItemId;
    }

    public String getSelectedItemName() {
        return selectedItemName;
    }
}