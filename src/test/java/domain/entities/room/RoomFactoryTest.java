package domain.entities.room;

import domain.entities.hint.Hint;
import domain.entities.interactable.Interactable;
import domain.entities.item.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoomFactoryTest {

    private CommonRoomFactory factory;

    @BeforeEach
    void setUp() {
        factory = new CommonRoomFactory();
    }

    @Test
    void testCreateRoom_MapsAllFieldsCorrectly() {
        // Arrange: Prepare distinct inputs for every single parameter
        String roomId = "room1";
        String description = "A room";
        String imagePath = "/images/Room.png";

        List<Interactable> interactables = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Hint> hints = new ArrayList<>();
        Map<String, Position> positions = new HashMap<>();

        // Add a dummy position to ensure the positions map isn't just empty
        Position samplePosition = new Position(10.0, 20.0);
        positions.put("door", samplePosition);

        // Act: Execute the factory method (this achieves 100% factory line coverage)
        Room createdRoom = factory.createRoom(
                roomId, description, imagePath, interactables, items, hints, positions
        );

        // Assert: Verify absolutely every field to guarantee structural integrity
        assertNotNull(createdRoom, "Factory should successfully instantiate the room");
        assertEquals(roomId, createdRoom.getId());
        assertEquals(description, createdRoom.getDescription());
        assertEquals(imagePath, createdRoom.getImagePath());

        // Assert the collections match exactly
        assertEquals(interactables, createdRoom.getInteractables());
        assertEquals(hints, createdRoom.getHints());

        // Fixes from original test: verifying items and positions mapping
        assertEquals(items, createdRoom.getItems());
        assertEquals(samplePosition, createdRoom.getPosition("door"));
    }
}