package interface_adapter.Interactable.Zoom;

public class ZoomState {
    private String name;
    private String description;
    private String sprite;
    private String interactLabel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getInteractLabel() {
        return interactLabel;
    }

    public void setInteractLabel(String interactLabel) {
        this.interactLabel = interactLabel;
    }
}
