package interface_adapter.Interactable.Interact;

import application.use_cases.Interactable.Interact.InteractInputBoundary;
import application.use_cases.Interactable.Interact.InteractInputData;

public class InteractController {
    private final InteractInputBoundary inputBoundary;

    public InteractController(InteractInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void interact(String interactableId) {
        InteractInputData interactInputData = new InteractInputData(interactableId);
        inputBoundary.interact(interactInputData);
    }
}
