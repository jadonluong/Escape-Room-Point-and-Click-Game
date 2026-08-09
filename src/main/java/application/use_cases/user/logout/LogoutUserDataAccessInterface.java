package application.use_cases.user.logout;

import domain.entities.user.User;

/**
 * The DAO of the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the user object of the curren user of the application.
     * @return the user object of the current user
     */
    User getCurrentUser();

    /**
     * Sets the user object indicating who is the current user of the application.
     * @param user the new current user
     */
    void setCurrentUser(User user);
}
