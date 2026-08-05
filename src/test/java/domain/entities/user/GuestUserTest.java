package domain.entities.user;

import domain.entities.User.GuestUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestUserTest extends AbstractUserTest<GuestUser> {

    @Override
    protected GuestUser createUser() {
        return new GuestUser();
    }

    @Test
    void testGetUsername() {
        assertInstanceOf(String.class, user.getUsername());
    }

    @Test
    void testIsRegistered() {
        assertFalse(user.isRegistered());
    }
}
