package application.use_cases.User.Login;

import domain.entities.User.CommonUserFunction;
import domain.entities.User.User;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);

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
    User getUser(String username);

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existByName(String username);
}
