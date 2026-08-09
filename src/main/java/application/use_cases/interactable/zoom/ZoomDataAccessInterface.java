package application.use_cases.interactable.zoom;

import domain.entities.interactable.Interactable;
import domain.entities.puzzle.Puzzle;

/**
 * Data access interface for retrieving entities required by the Zoom use case.
 */
public interface ZoomDataAccessInterface {

    /**
     * Retrieves an interactable by its ID.
     *
     * @param interactableId the ID of the interactable to retrieve
     * @return the interactable associated with the specified ID
     */
    Interactable getInteractableById(String interactableId);

    /**
     * Retrieves a puzzle by its ID.
     *
     * @param puzzleId the ID of the puzzle to retrieve
     * @return the puzzle associated with the specified ID
     */
    Puzzle getPuzzleById(String puzzleId);
}
