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

        String interactLabel = "Interact";
        if (interactable.getRewardItemId() == null && interactable.getSuccessMessage() == null
                && interactable.getUnlockedRoomId() == null && interactable.getLinkedPuzzleId() == null) {
            interactLabel = null;
        } else if (interactable.needsItem() && !interactable.isInteracted()) {
            interactLabel = "Use Item";
        } else if (interactable.getUnlockedRoomId() != null && interactable.isInteracted()) {
            interactLabel = "Go Through";
        } else if (interactable.getLinkedPuzzleId() != null && interactable.isInteracted()) {
            interactLabel = "Enter Puzzle";
        }

        ZoomOutputData outputData = new ZoomOutputData(interactable.getName(), interactable.getDescription(),
                interactable.getSprite(), interactLabel);
        outputBoundary.prepareZoomInView(outputData);
    }

    @Override
    public void zoomOut() {
        outputBoundary.prepareZoomOutView(); // Zoom out to the current Room!
    }
}
