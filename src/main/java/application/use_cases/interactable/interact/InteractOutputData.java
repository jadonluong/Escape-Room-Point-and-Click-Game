package application.use_cases.interactable.interact;

/**
 * Output data containing the result of an interaction.
 *
 * <p>Includes information about the interaction message, any reward or
 * selected items, and the interactable involved.</p>
 */
public class InteractOutputData {
    private final String successMessage;

    // For InventoryState
    private final String rewardItemId;
    private final String rewardItemName;
    private final String selectedItemId;
    private final String selectedItemName;

    private final String interactableId;
    private final String interactableSprite;

    /**
     * Creates output data for an interaction.
     *
     * @param successMessage the message describing the successful interaction
     * @param rewardItemId the ID of the item rewarded by the interaction
     * @param rewardItemName the name of the item rewarded by the interaction
     * @param selectedItemId the ID of the item selected for the interaction
     * @param selectedItemName the name of the item selected for the interaction
     * @param interactableId the ID of the interactable involved in the interaction
     */
    public InteractOutputData(
            String successMessage,
            String rewardItemId,
            String rewardItemName,
            String selectedItemId,
            String selectedItemName,
            String interactableId,
            String interactableSprite) {
        this.successMessage = successMessage;
        this.rewardItemId = rewardItemId;
        this.rewardItemName = rewardItemName;
        this.selectedItemId = selectedItemId;
        this.selectedItemName = selectedItemName;
        this.interactableId = interactableId;
        this.interactableSprite = interactableSprite;
    }

    /**
     * Returns the success message for the interaction.
     *
     * @return the success message
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * Returns the ID of the reward item.
     *
     * @return the reward item ID
     */
    public String getRewardItemId() {
        return rewardItemId;
    }

    /**
     * Returns the name of the reward item.
     *
     * @return the reward item name
     */
    public String getRewardItemName() {
        return rewardItemName;
    }

    /**
     * Returns the ID of the selected item.
     *
     * @return the selected item ID
     */
    public String getSelectedItemId() {
        return selectedItemId;
    }

    /**
     * Returns the name of the selected item.
     *
     * @return the selected item name
     */
    public String getSelectedItemName() {
        return selectedItemName;
    }

    /**
     * Returns the ID of the interactable involved in the interaction.
     *
     * @return the interactable ID
     */
    public String getInteractableId() {
        return interactableId;
    }

    /**
     * Returns the sprite of the interactable involved in the interaction.
     *
     * @return the interactable sprite
     */
    public String getInteractableSprite() {
        return interactableSprite;
    }
}
