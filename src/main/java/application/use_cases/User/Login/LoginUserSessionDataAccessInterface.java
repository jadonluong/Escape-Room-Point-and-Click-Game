package application.use_cases.User.Login;

import domain.entities.User.User;

public interface LoginUserSessionDataAccessInterface {

    /**
     * Saves the current user
     * @param user the user object that was initiated from db
     */
    void setCurrentUser(User user);
}
