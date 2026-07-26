package application.use_cases.Interactable.Zoom;

public class ZoomOutputData {
    private String name;
    private String description;
    private String sprite;
    private String interactLabel; // What should the Interact button say?

    public ZoomOutputData(String name, String description, String sprite, String interactLabel) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
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

    public String getInteractLabel() {
        return interactLabel;
    }
}
