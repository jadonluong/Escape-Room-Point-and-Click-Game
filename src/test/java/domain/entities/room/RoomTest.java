package domain.entities.room;

import domain.entities.hint.CommonHint;
import domain.entities.hint.Hint;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    private Room room;
    private Item item;
    private Interactable interactable;
    private Hint hint;

    @BeforeEach
    void setUp(){
        List<String> messages = new ArrayList<>();
        messages.add("message");
        this.hint = new CommonHint("hint1",
                "/images/ui/buttons/QuickButton.png",
                messages);
        this.item = new CommonItem("key1",
                "/images/ui/buttons/QuickButton.png",
                "description",
                true,
                "/images/ui/buttons/QuickButton.png");
        this.interactable = new CommonInteractable(
                "door1",
                "Wooden Door",
                "A sturdy wooden door.",
                "/images/ui/buttons/QuickButton.png",
                "Unlocked Door",
                "The door is now wide open.",
                "/images/ui/buttons/TutorialButton.png",
                false,
                true,
                true,
                "brass_key",
                "reward_coin",
                "puzzle_01",
                "room_02",
                "You used the key and unlocked the door!"
        );


        this.room = new CommonRoom("room", "description", "path"
        , new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), new HashMap<>());

    }

    @Test
    void testRoomInfo(){
        assertEquals("room", room.getId());
        assertEquals("description", room.getDescription());
    }
    // Interactable test
    @Test
    void testInteractable() {

        // Test the add, get, remove behavior related to interactable.
        room.addInteractable(interactable);
        List<Interactable> result = room.getInteractables();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(interactable));
        assertEquals("door1", interactable.getId());

        room.removeInteractable(interactable.getId());
        assertEquals(0, result.size());

        room.removeInteractable(interactable.getId());
        assertEquals(0, result.size());
    }

    @Test
    void testInteractableGetById() {
        // Test the getter that use String as parameter.
        room.addInteractable(interactable);
        Interactable result = room.getInteractableById("door1");
        assertNotNull(result);
        assertEquals("door1", result.getId());

        Interactable result2 = room.getInteractableById("invalidId");
        assertNull(result2);
    }



    // Item test
    @Test
    void testAddItem() {
        room.addItem(item);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        assertEquals(room.getItems(), items);
        assertEquals(1, room.getItems().size());
    }

    @Test
    void testRemoveItem() {
        room.addItem(item);
        room.removeItem(item);
        assertTrue(room.getItems().isEmpty());
    }

    @Test
    void testRemoveNonExistentItem() {
        room.addItem(item);
        Item item2 = new CommonItem("key2",
                "/images/ui/buttons/QuickButton.png",
                "description",
                true,
                "/images/ui/buttons/QuickButton.png");
        room.removeItem(item2);
        assertEquals(1, room.getItems().size());
    }

    // Hint test

    @Test
    void testGetHint() {
        room.setHint(hint);
        List<Hint> result = room.getHints();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(hint));
    }

    @Test
    void testImgPath(){
        assertEquals("path", room.getImagePath());
    }

    @Test
    void testPosition(){
        // 1. Setup room with an initial position map
        Map<String, Position> initialMap = new HashMap<>();
        Position pos1 = new Position(10.0, 20.0);
        Position pos2 = new Position(30.0, 40.0);
        Position pos3 = new Position(50.0, 60.0);
        initialMap.put("door", pos1);

        CommonRoom room = new CommonRoom("r1", "desc", "img.png",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), initialMap);

        // 2. Verify getPosition (Initial State)
        assertEquals(pos1, room.getPosition("door"));

        // 3. Verify setPosition (Single Add)
        room.setPosition("window", pos2);
        assertEquals(pos2, room.getPosition("window"));

        // 4. Verify setPositions (Bulk Add)
        Map<String, Position> bulkMap = new HashMap<>();
        bulkMap.put("desk", pos3);
        room.setPositions(bulkMap);

        assertEquals(pos3, room.getPosition("desk"));
    }

}
