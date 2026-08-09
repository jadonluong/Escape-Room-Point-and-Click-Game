package application.use_cases.game_play;

import domain.entities.user.User;

public interface UserDataAccessInterface {

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the current user
     */
    User getCurrentUser();
}
