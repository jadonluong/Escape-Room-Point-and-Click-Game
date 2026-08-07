package application.use_cases.User.SaveProgress;

import domain.entities.User.User;

public interface SaveProgressUserSessionDataAccessInterface {

    /**
     * Returns the live user.
     * @return the live user currently running the program
     */
    User getCurrentUser();
}
