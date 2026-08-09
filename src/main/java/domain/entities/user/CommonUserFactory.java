package domain.entities.user;

/**
 * Factory for creating and restoring common users.
 */
public interface CommonUserFactory {

    /**
     * Creates a new common user.
     * @param username the username of the new common user.
     * @param password the password of the new common user.
     * @return the new common user.
     */
    User createCommonUser(String username, String password);
}
