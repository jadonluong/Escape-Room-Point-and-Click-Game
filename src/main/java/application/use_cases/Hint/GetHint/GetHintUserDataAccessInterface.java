package application.use_cases.Hint.GetHint;

import domain.entities.User.User;

public interface GetHintUserDataAccessInterface {

    /**
     * Returns the live user.
     * @return the live user currently playing the game
     */
    User getCurrentUser();
}
