package application.use_cases.user.login;

import domain.entities.user.User;

public interface LoginUserSessionDataAccessInterface {

    /**
     * Saves the current user.
     * @param user the user object that was initiated from db
     */
    void setCurrentUser(User user);
}
