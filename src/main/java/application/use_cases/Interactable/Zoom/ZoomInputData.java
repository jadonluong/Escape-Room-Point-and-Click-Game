package application.use_cases.Interactable.Zoom;

import domain.entities.User.User;

public class ZoomInputData {
    private User user;
    private String interactableId;

    public ZoomInputData(User user, String interactableId) {
        this.user = user;
        this.interactableId = interactableId;
    }

    public User getUser() {
        return user;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
