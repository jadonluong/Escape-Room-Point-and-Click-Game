package domain.UseCases.User;

import application.use_cases.User.LiveUserSessionTracking;
import domain.entities.User.CommonUser;
import domain.entities.User.GuestUser;
import domain.entities.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LiveUserSessionTrackingTest {
    private LiveUserSessionTracking liveUserSessionTracking;

    private User testCommonUser;
    private User testGuestUser;

    @BeforeEach
    void setUp() {
        testCommonUser = new CommonUser("Maki", "Mai");
        testGuestUser = new GuestUser();
        liveUserSessionTracking = new LiveUserSessionTracking();
    }

    @Test
    void testCommonUserSessionTracking() {
        assertNull(liveUserSessionTracking.getCurrentUser());
        liveUserSessionTracking.setCurrentUser(testCommonUser);
        assertNotNull(liveUserSessionTracking.getCurrentUser());
        assertEquals(testCommonUser, liveUserSessionTracking.getCurrentUser());
    }

    @Test
    void testGuestUserSessionTracking() {
        assertNull(liveUserSessionTracking.getCurrentUser());
        liveUserSessionTracking.setCurrentUser(testGuestUser);
        assertNotNull(liveUserSessionTracking.getCurrentUser());
        assertEquals(testGuestUser, liveUserSessionTracking.getCurrentUser());
    }
}
