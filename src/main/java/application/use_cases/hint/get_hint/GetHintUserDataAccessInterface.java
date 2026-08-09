package application.use_cases.hint.get_hint;

import domain.entities.user.User;

public interface GetHintUserDataAccessInterface {

    /**
     * Returns the live user.
     * @return the live user currently playing the game
     */
    User getCurrentUser();
}
