package domain.entities.User;

/**
 * The interface with methods related to the user's identity.
 */
public interface UserIdentity {

    /**
     * Returns the username of the user.
     * @return the registered username if the user is a common user, the guest id if the user is a guest user.
     */
    String getUsername();

    /**
     * Returns the type of user this user belongs to.
     * @return true if the user is a common user, false if the user is a guest user.
     */
    boolean isRegistered();
}
