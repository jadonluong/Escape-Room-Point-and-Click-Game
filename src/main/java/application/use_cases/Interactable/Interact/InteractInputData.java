package application.use_cases.Interactable.Interact;

import domain.entities.User.User;

public class InteractInputData {
    private User user;
    private String interactableId;

    public InteractInputData(User user, String interactableId) {
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
