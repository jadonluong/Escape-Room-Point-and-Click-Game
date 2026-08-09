package domain.entities.user;

/**
 * Factory for creating guest users.
 */
public interface GuestUserFactory {

    /**
     * Creates a new guest user.
     * @return the new guest user.
     */
    User createGuestUser();
}
