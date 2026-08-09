package application.use_cases.interactable.interact;

import domain.entities.user.User;

/**
 * Data access interface for retrieving the current user
 * for the Interact use case.
 */
public interface InteractUserDataAccessInterface {

    /**
     * Retrieves the currently active user.
     *
     * @return the current user
     */
    User getCurrentUser();
}
