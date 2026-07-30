package application.use_cases.User.Login;

import domain.entities.User.CommonUser;
import domain.entities.User.CommonUserFunction;
import domain.entities.User.User;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Returns the function interface that gets the password of the user with the given username
     * @return the function interface that gets the user's password
     */
    CommonUserFunction getUserPassword(String username);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    CommonUser getUser(String username);

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the current user
     * @param user the user object that was initiated from db
     */
    void setCurrentUser(User user);
}
