package application.use_cases.Interactable.Zoom;

import domain.entities.Interactable.Interactable;
import domain.entities.Puzzle.Puzzle;

/**
 * Interactor responsible for handling zooming in on and out of interactable
 * objects.
 */
public class ZoomInteractor implements ZoomInputBoundary {
    private final ZoomDataAccessInterface dataAccess;
    private final ZoomOutputBoundary outputBoundary;

    /**
     * Creates a ZoomInteractor with the specified data access interface
     * and output boundary.
     *
     * @param dataAccess the data access interface used to retrieve interactables
     *                   and puzzles
     * @param outputBoundary the output boundary used to present zoom views
     */
    public ZoomInteractor(
            ZoomDataAccessInterface dataAccess,
            ZoomOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Zooms in on an interactable specified by the input data and prepares
     * the corresponding zoom view.
     *
     * @param inputData the input data containing the ID of the interactable
     */
    @Override
    public void zoomIn(ZoomInputData inputData) {
        String interactableId = inputData.getInteractableId();
        Interactable interactable = dataAccess.getInteractableById(interactableId);

        String puzzleId = interactable.getLinkedPuzzleId();
        Puzzle puzzle = dataAccess.getPuzzleById(puzzleId);

        String interactLabel = getInteractLabel(interactable, puzzleId, puzzle);

        ZoomOutputData outputData = new ZoomOutputData(
                interactable.getName(),
                interactable.getDescription(),
                interactable.getSprite(),
                interactLabel,
                interactableId,
                puzzleId);

        outputBoundary.prepareZoomInView(outputData);
    }

    /**
     * Determines the interaction label to display for an interactable based
     * on its current state and any associated puzzle.
     *
     * @param interactable the interactable being displayed
     * @param puzzleId the ID of the puzzle linked to the interactable
     * @param puzzle the puzzle linked to the interactable
     * @return the appropriate interaction label, or {@code null} if the
     *         interactable has no available interaction
     */
    private String getInteractLabel(
            Interactable interactable, String puzzleId, Puzzle puzzle) {

        String value;

        if (hasNoInteraction(interactable)) {
            value = null;
        }
        else if (interactable.needsItem() && !interactable.isInteracted()) {
            value = "Use Item";
        }
        else if (interactable.getUnlockedRoomId() != null
                && interactable.isInteracted()) {
            value = "Go Through";
        }
        else if (puzzleId != null
                && interactable.isInteracted()
                && !puzzle.isSolved()) {
            value = "Enter Puzzle";
        }
        else {
            value = "Interact";
        }

        return value;
    }

    /**
     * Determines whether an interactable has no available interaction.
     *
     * @param interactable the interactable to check
     * @return {@code true} if the interactable has no reward, success message,
     *         unlocked room, or linked puzzle; {@code false} otherwise
     */
    private boolean hasNoInteraction(Interactable interactable) {
        return interactable.getRewardItemId() == null
                && interactable.getSuccessMessage() == null
                && interactable.getUnlockedRoomId() == null
                && interactable.getLinkedPuzzleId() == null;
    }

    /**
     * Zooms out of the current interactable and prepares the current room view.
     */
    @Override
    public void zoomOut() {
        // Zoom out to the current Room!
        outputBoundary.prepareZoomOutView();
    }
}
