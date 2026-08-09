package domain.entities.user;

/**
 * Factory for creating GuestUser objects.
 */
public class GuestUserFactoryClass implements GuestUserFactory {

    @Override
    public User createGuestUser() {
        return new GuestUser();
    }
}
