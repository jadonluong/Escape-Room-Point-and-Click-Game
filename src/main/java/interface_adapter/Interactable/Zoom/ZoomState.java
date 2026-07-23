package interface_adapter.Interactable.Zoom;

public class ZoomState {
    private String name;
    private String description;
    private String sprite;

    private boolean canInteract;
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

    public boolean canInteract() {
        return canInteract;
    }

    public void setCanInteract(boolean canInteract) {
        this.canInteract = canInteract;
    }

    public String getInteractLabel() {
        return interactLabel;
    }

    public void setInteractLabel(String interactLabel) {
        this.interactLabel = interactLabel;
    }
}
