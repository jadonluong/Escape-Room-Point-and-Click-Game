package application.use_cases.Interactable.Zoom;

/**
 * Output data for the Zoom use case.
 *
 * <p>Contains the information required to display an interactable in the
 * zoomed-in view.</p>
 */
public class ZoomOutputData {
    private String name;
    private String description;
    private String sprite;
    private String interactLabel;
    // What should the Interact button say?

    private String interactableId;
    private String puzzleId;

    /**
     * Creates output data for the zoomed-in interactable view.
     *
     * @param name the name of the interactable
     * @param description the description of the interactable
     * @param sprite the sprite used to display the interactable
     * @param interactLabel the label to display on the interaction button
     * @param interactableId the ID of the interactable
     * @param puzzleId the ID of the puzzle linked to the interactable
     */
    public ZoomOutputData(
            String name,
            String description,
            String sprite,
            String interactLabel,
            String interactableId,
            String puzzleId) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
        this.interactLabel = interactLabel;
        this.interactableId = interactableId;
        this.puzzleId = puzzleId;
    }

    /**
     * Returns the name of the interactable.
     *
     * @return the interactable name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the interactable.
     *
     * @return the interactable description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the sprite used to display the interactable.
     *
     * @return the interactable sprite
     */
    public String getSprite() {
        return sprite;
    }

    /**
     * Returns the label to display on the interaction button.
     *
     * @return the interaction label
     */
    public String getInteractLabel() {
        return interactLabel;
    }

    /**
     * Returns the ID of the interactable.
     *
     * @return the interactable ID
     */
    public String getInteractableId() {
        return interactableId;
    }

    /**
     * Returns the ID of the puzzle linked to the interactable.
     *
     * @return the puzzle ID
     */
    public String getPuzzleId() {
        return puzzleId;
    }
}
