package application.use_cases.Interactable.Zoom;

import domain.entities.Interactable.Interactable;
import domain.entities.User.User;

public class ZoomInteractor implements ZoomInputBoundary {
    private ZoomDataAccessInterface dataAccess;
    private ZoomOutputBoundary outputBoundary;

    public ZoomInteractor(ZoomDataAccessInterface dataAccess, ZoomOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void zoomIn(ZoomInputData inputData) {
        Interactable interactable = dataAccess.getInteractableById(inputData.getInteractableId());

        boolean canInteract = true;
        if (interactable.getRewardItemId() == null && interactable.getSuccessMessage() == null
                && interactable.getUnlockedRoomId() == null && interactable.getLinkedPuzzleId() == null) {
            canInteract = false;
        }

        String interactLabel = "Interact";
        if (!canInteract) {
            interactLabel = null;
        } else if (interactable.needsItem() && !interactable.isInteracted()) {
            interactLabel = "Use Item";
        } else if (interactable.getUnlockedRoomId() != null && interactable.isInteracted()) {
            interactLabel = "Go Through";
        } else if (interactable.getLinkedPuzzleId() != null && interactable.isInteracted()) {
            interactLabel = "Enter Puzzle";
        }

        ZoomOutputData outputData = new ZoomOutputData(interactable.getName(), interactable.getDescription(),
                interactable.getSprite(), canInteract, interactLabel);
        outputBoundary.prepareZoomInView(outputData);
    }

    @Override
    public void zoomOut(ZoomInputData inputData) {
        User player = dataAccess.getUserById(inputData.getUserId());
        outputBoundary.prepareZoomOutView(player.getCurrentRoomID()); // Zoom out to the current Room!
    }
}
