package domain.entities.user;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactoryClass implements CommonUserFactory {

    @Override
    public User createCommonUser(String username, String password) {
        return new CommonUser(username, password);
    }
}
