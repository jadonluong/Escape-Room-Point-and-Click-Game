package domain.entities.user;

import domain.entities.Interactable.Interactable;
import domain.entities.Room.CommonRoom;
import domain.entities.Room.Room;
import domain.entities.User.AbstractUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractUserTest<T extends AbstractUser> {
    protected T user;
    protected abstract T createUser();
    protected Room testRoom;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    @Test
    void testActiveGameModeSetterAndGetter() {
        user.setActiveGameMode("StoryMode");
        assertEquals("StoryMode", user.getActiveGameMode());
        user.setActiveGameMode("QuickMode");
        assertEquals("QuickMode", user.getActiveGameMode());
    }

    @Test
    void testUnlockRoomStoryModeEmpty() {
        Room room = new CommonRoom("room1",
                "the test room",
                "fake_path",
                interactables,
                items,
                hints);
        user.setActiveGameMode("StoryMode");
        user.unlockRoom(room);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        assertEquals(roomList, user.getRoomsUnlocked());
    }
}
