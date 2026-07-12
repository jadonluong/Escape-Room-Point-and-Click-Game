package domain.entities.User;

/**
 * Factory for creating users.
 */
public interface UserFactory {

    /**
     * Creates a new guest user.
     * @return the new guest user.
     */
    GuestUser createGuestUser();

    /**
     * Creates a new common user.
     * @param username the username of the new common user.
     * @param password the password of the new common user
     * @return the new common user.
     */
    CommonUser createCommonUser(String username, String password);
}
