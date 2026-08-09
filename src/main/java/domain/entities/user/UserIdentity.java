package domain.entities.user;

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

    /**
     * Sets the game mode the user is in.
     * @param mode the game mode the user is in
     */
    void setActiveGameMode(String mode);

    /**
     * Returns the game mode the user is in.
     * @return the game mode the user is in
     */
    String getActiveGameMode();
}
