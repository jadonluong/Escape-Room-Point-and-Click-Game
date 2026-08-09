package application.use_cases.interactable.interact;

import domain.entities.interactable.Interactable;
import domain.entities.item.Item;
import domain.entities.puzzle.Puzzle;
import domain.entities.room.Room;

/**
 * Data access interface for retrieving entities required by the Interact use case.
 */
public interface InteractDataAccessInterface {

    /**
     * Retrieves a room by its ID.
     *
     * @param roomId the ID of the room to retrieve
     * @return the room associated with the specified ID
     */
    Room getRoomById(String roomId);

    /**
     * Retrieves an interactable object by its ID.
     *
     * @param interactableId the ID of the interactable to retrieve
     * @return the interactable associated with the specified ID
     */
    Interactable getInteractableById(String interactableId);

    /**
     * Retrieves an item by its ID.
     *
     * @param itemId the ID of the item to retrieve
     * @return the item associated with the specified ID
     */
    Item getItemById(String itemId);

    /**
     * Retrieves a puzzle by its ID.
     *
     * @param puzzleId the ID of the puzzle to retrieve
     * @return the puzzle associated with the specified ID
     */
    Puzzle getPuzzleById(String puzzleId);
}
