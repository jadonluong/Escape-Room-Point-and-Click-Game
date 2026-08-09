package application.use_cases.interactable.zoom;

/**
 * Input data for the Zoom use case.
 */
public class ZoomInputData {
    private String interactableId;

    /**
     * Creates zoom input data for the specified interactable.
     *
     * @param interactableId the ID of the interactable to zoom in on
     */
    public ZoomInputData(String interactableId) {
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
