package application.use_cases.registries;

import application.game_registry.RoomRegistry;
import domain.entities.room.Room;
import domain.entities.room.CommonRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RoomRegistryTest {
    private TestRoomRegistry testRegistry;
    private String validRoomId1;
    private String validRoomId2;
    private Room testRoom1;
    private Room testRoom2;

    private static class TestRoomRegistry implements RoomRegistry {
        private final Map<String, Room> registry = new HashMap<>();

        public void addRoom(String roomId, Room room) {
            registry.put(roomId, room);
        }

        @Override
        public Room getRoomById(String roomID) {
            return registry.get(roomID);
        }
    }

    @BeforeEach
    void setup() {
        testRegistry = new TestRoomRegistry();

        validRoomId1 = "room1";
        validRoomId2 = "room2";

        this.testRoom1 = (Room) new CommonRoom("room1", "test room 1", "fake_room_path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        this.testRoom2 = (Room) new CommonRoom("room2", "test room 2", "fake_room_path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        testRegistry.addRoom(validRoomId1, testRoom1);
        testRegistry.addRoom(validRoomId2, testRoom2);
    }

    @Test
    void testGetRoomByIdSuccess() {
        Room retrievedRoom = testRegistry.getRoomById(validRoomId1);

        assertNotNull(retrievedRoom, "Room should be retrieved successfully.");
        assertEquals(testRoom1, retrievedRoom, "Retrieved room should match the registered instance.");
    }

    @Test
    void testGetRoomByIdNotFound() {
        Room retrievedRoom = testRegistry.getRoomById("non_existent_room");

        assertNull(retrievedRoom, "Retrieving an unregistered room ID should return null.");
    }

    @Test
    void testGetRoomByIdMultipleEntries() {
        Room room1 = testRegistry.getRoomById(validRoomId1);
        Room room2 = testRegistry.getRoomById(validRoomId2);

        assertNotNull(room1);
        assertNotNull(room2);
        assertNotEquals(room1, room2, "Registry should distinguish between different room IDs.");
    }
}
