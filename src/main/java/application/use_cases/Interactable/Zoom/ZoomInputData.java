package application.use_cases.Interactable.Zoom;

public class ZoomInputData {
    private String userId;
    private String interactableId;

    public ZoomInputData(String userId, String interactableId) {
        this.userId = userId;
        this.interactableId = interactableId;
    }

    public String getUserId() {
        return userId;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
