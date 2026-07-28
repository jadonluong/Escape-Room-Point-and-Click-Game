package interface_adapter.Interactable.Interact;

import application.use_cases.Interactable.Interact.InteractInputBoundary;
import application.use_cases.Interactable.Interact.InteractInputData;
import domain.entities.User.User;

public class InteractController {
    private final InteractInputBoundary inputBoundary;

    public InteractController(InteractInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void interact(User user, String interactableId) {
        InteractInputData interactInputData = new InteractInputData(user, interactableId);
        inputBoundary.interact(interactInputData);
    }
}
