package application.game_registry;

import domain.entities.Interactable.Interactable;

/**
 * Registry for getting Interactable objects using their IDs.
 */
public interface InteractableRegistry {

    /**
     * Returns the Interactable object with the given ID.
     * @param ID the ID of the Interactable object to be retrieved
     * @return the Interactable object
     */
    Interactable getInteractableById(String ID);
}
