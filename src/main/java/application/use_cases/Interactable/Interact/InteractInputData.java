package application.use_cases.Interactable.Interact;

public class InteractInputData {
    private String userId;
    private String interactableId;

    public InteractInputData(String userId, String interactableId) {
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
