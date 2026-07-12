package domain.entities.User;

/**
 * Factory for creating GuestUser and CommonUser objects.
 */
public class UserFactoryClass implements UserFactory {

    @Override
    public GuestUser createGuestUser() {
        return new GuestUser();
    }

    @Override
    public CommonUser createCommonUser(String username, String password) {
        return new CommonUser(username, password);
    }
}
