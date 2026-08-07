package application.use_cases.Interactable.Interact;

import domain.entities.User.User;

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
