package application.use_cases.user.save_progress;

import domain.entities.user.User;

public interface SaveProgressUserSessionDataAccessInterface {

    /**
     * Returns the live user.
     * @return the live user currently running the program
     */
    User getCurrentUser();
}
