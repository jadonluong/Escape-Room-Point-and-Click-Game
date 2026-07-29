package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

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
    void testForFieldsMapping() {
        String roomId = "room1";
        String description = "A room";
        String imagePath = "/images/Room.png";
        List<Interactable> interactables = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Hint> hints = new ArrayList<>();
        Map<String, Position> positions = new HashMap<>();

        Room createdRoom = factory.createRoom(
                roomId, description, imagePath, interactables, items, hints, positions
        );

        assertNotNull(createdRoom);
        assertEquals(roomId, createdRoom.getId());
        assertEquals(description, createdRoom.getDescription());
        assertEquals(imagePath, createdRoom.getImagePath());
        assertEquals(interactables, createdRoom.getInteractables());
        assertEquals(hints, createdRoom.getHints());
    }
}