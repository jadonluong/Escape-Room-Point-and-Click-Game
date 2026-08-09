package interface_adapter.interactable.interact;

import application.use_cases.interactable.interact.InteractInputBoundary;
import application.use_cases.interactable.interact.InteractInputData;

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
