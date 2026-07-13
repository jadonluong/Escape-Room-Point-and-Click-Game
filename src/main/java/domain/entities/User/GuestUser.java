package domain.entities.User;

import java.util.UUID;

/**
 * The GuestUser class that extends the AbstractUser class.
 */
public class GuestUser extends AbstractUser {
    private String guestID;

    public GuestUser() {
        super();
        this.guestID = "GUEST_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String getUsername() {
        return this.guestID;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
