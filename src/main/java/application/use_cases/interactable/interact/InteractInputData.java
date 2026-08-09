package application.use_cases.interactable.interact;

/**
 * Input data for the Interact use case.
 */
public class InteractInputData {
    private final String interactableId;

    /**
     * Creates interaction input data for the specified interactable.
     *
     * @param interactableId the ID of the interactable to interact with
     */
    public InteractInputData(String interactableId) {
        this.interactableId = interactableId;
    }

    /**
     * Returns the ID of the interactable.
     *
     * @return the interactable ID
     */
    public String getInteractableId() {
        return interactableId;
    }
}
