package domain.entities.user;

import domain.entities.User.GuestUser;
import domain.entities.User.GuestUserFactory;
import domain.entities.User.GuestUserFactoryClass;
import domain.entities.User.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestUserFactoryTest {

    @Test
    void testCreateGuestUser_ShouldReturnNonNullGuestUserInstance() {
        GuestUserFactory factory = new GuestUserFactoryClass();

        User result = factory.createGuestUser();

        assertNotNull(result, "Factory should not return null.");
        assertInstanceOf(GuestUser.class, result, "Factory should return a concrete GuestUser instance.");
    }
}
