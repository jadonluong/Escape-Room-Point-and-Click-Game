package domain.entities.User;

/**
 * Factory for creating GuestUser objects.
 */
public class GuestUserFactoryClass implements GuestUserFactory {

    @Override
    public User createGuestUser() {
        return new GuestUser();
    }
}
