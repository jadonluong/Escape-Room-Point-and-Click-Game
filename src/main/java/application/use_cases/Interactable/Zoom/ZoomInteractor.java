package application.use_cases.Interactable.Zoom;

import domain.entities.Interactable.Interactable;
import domain.entities.Puzzle.Puzzle;
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
        String interactableId = inputData.getInteractableId();
        Interactable interactable = dataAccess.getInteractableById(interactableId);

        String puzzleId = interactable.getLinkedPuzzleId();
        Puzzle puzzle = dataAccess.getPuzzleById(puzzleId);

        String interactLabel = "Interact";
        if (interactable.getRewardItemId() == null && interactable.getSuccessMessage() == null
                && interactable.getUnlockedRoomId() == null && interactable.getLinkedPuzzleId() == null) {
            interactLabel = null;
        } else if (interactable.needsItem() && !interactable.isInteracted()) {
            interactLabel = "Use Item";
        } else if (interactable.getUnlockedRoomId() != null && interactable.isInteracted()) {
            interactLabel = "Go Through";
        } else if (puzzleId != null && interactable.isInteracted() && !puzzle.isSolved()) {
            interactLabel = "Enter Puzzle";
        }

        ZoomOutputData outputData = new ZoomOutputData(interactable.getName(), interactable.getDescription(),
                interactable.getSprite(), interactLabel, inputData.getUserId(), interactableId, puzzleId);
        outputBoundary.prepareZoomInView(outputData);
    }

    @Override
    public void zoomOut() {
        outputBoundary.prepareZoomOutView(); // Zoom out to the current Room!
    }
}
