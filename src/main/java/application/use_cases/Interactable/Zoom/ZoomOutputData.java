package application.use_cases.Interactable.Zoom;

public class ZoomOutputData {
    private String name;
    private String description;
    private String sprite;

    private boolean canInteract; // Can the player Interact? This check is for Interactables that ONLY have Zoom.
    private String interactLabel; // What should the Interact button say?

    public ZoomOutputData(String name, String description, String sprite, boolean canInteract, String interactLabel) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
        this.canInteract = canInteract;
        this.interactLabel = interactLabel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSprite() {
        return sprite;
    }

    public boolean canInteract() {
        return canInteract;
    }

    public String getInteractLabel() {
        return interactLabel;
    }
}
