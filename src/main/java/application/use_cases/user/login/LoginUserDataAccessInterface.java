package application.use_cases.user.login;

import domain.entities.user.CommonUser;
import domain.entities.user.CommonUserFunction;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Returns the function interface that gets the password of the user with the given username.
     * @param username the username entered
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
}
