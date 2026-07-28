package application.use_cases.Interactable.Zoom;

public class ZoomOutputData {
    private String name;
    private String description;
    private String sprite;
    private String interactLabel; // What should the Interact button say?

    private String userId;
    private String interactableId;
    private String puzzleId;

    public ZoomOutputData(String name, String description, String sprite, String interactLabel, String userId,
                          String interactableId, String puzzleId) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
        this.interactLabel = interactLabel;
        this.userId = userId;
        this.interactableId = interactableId;
        this.puzzleId = puzzleId;
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

    public String getInteractLabel() {
        return interactLabel;
    }

    public String getUserId() {
        return userId;
    }

    public String getInteractableId() {
        return interactableId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }
}
