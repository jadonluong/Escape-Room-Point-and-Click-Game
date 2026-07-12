package domain.entities.User;

/**
 * The GuestUser class that extends the AbstractUser class.
 */
public class GuestUser extends AbstractUser{

    public GuestUser() {
        super();
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
