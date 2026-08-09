package domain.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUserFactoryTest {

    @Test
    void testCreateCommonUser_ShouldReturnPopulatedCommonUserInstance() {
        CommonUserFactory factory = new CommonUserFactoryClass();
        String expectedUser = "test_user";
        String expectedPass = "secure_password_123";

        User result = factory.createCommonUser(expectedUser, expectedPass);

        assertNotNull(result, "Factory should not return null.");
        assertInstanceOf(CommonUser.class, result, "Factory should return a concrete CommonUser instance.");

        // Cast to CommonUser to check that the fields were passed correctly
        CommonUser commonUser = (CommonUser) result;
        assertEquals(expectedUser, commonUser.getUsername(), "Username was not correctly passed to the instance.");
        assertEquals(expectedPass, commonUser.getPassword(), "Password was not correctly passed to the instance.");
    }
}
