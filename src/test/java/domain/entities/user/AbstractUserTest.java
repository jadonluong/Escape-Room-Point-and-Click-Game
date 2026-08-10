package domain.entities.user;

import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.room.CommonRoom;
import domain.entities.room.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUserTest<T extends AbstractUser> {
    protected T user;
    protected abstract T createUser();

        /*
        Object creation:
        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");

        Interactable interactable = new CommonInteractable("1",
                "book",
                "A text book",
                "fake_default_sprite",
                "Open book",
                "An open text book",
                "fake_interacted_sprite",
                false, true, false,
                null, null, null, null, "Congrats");

        List<String> messages = new ArrayList<>();
        messages.add("message1");
        Hint hint = new CommonHint("1", "fake_hint_path", messages);

        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        */

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
    void testSaveAndGetCurrentRoomID() {
        assertNull( user.getCurrentRoomID());

        user.saveCurrentRoomID("room1");
        assertEquals("room1", user.getCurrentRoomID());

        user.setActiveGameMode("StoryMode");
        user.saveCurrentRoomID("room1");
        assertEquals("room1", user.getCurrentRoomID());
        assertEquals("room1", user.getStoryModeCurrentRoomID());
    }

    @Test
    void testSaveAndGetSelectedItemID() {
        assertNull(user.getSelectedItemID());

        user.saveSelectedItemID("item1");
        assertEquals("item1", user.getSelectedItemID());
    }

    @Test
    void testUnlockRoomStoryMode() {
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(),  new ArrayList<>(), new HashMap<>());

        user.setActiveGameMode("StoryMode");
        user.unlockRoom(room);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(null);
        assertEquals(roomList, user.getRoomsUnlocked());
    }

    @Test
    void testUnlockRoomQuickMode() {
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        user.setActiveGameMode("QuickMode");
        user.unlockRoom(room);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(null);
        assertEquals(roomList, user.getRoomsUnlocked());
    }

    @Test
    void testUnlockRoomTutorialMode() {
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        user.setActiveGameMode("TutorialMode");

        user.unlockRoom(room);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(room);
        assertEquals(roomList, user.getRoomsUnlocked());

        user.unlockRoom(null);
        assertEquals(roomList, user.getRoomsUnlocked());
    }

    @Test
    void testUnlockRoomNoMode() {
        assertEquals(new ArrayList<>(), user.getRoomsUnlocked());

        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        user.unlockRoom(room);

        assertEquals(new ArrayList<>(), user.getRoomsUnlocked());
    }

    @Test
    void testSaveItemStoryMode(){
        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.setActiveGameMode("StoryMode");

        ArrayList<Item> itemList = new ArrayList<>();
        assertEquals(itemList, user.getItemInventory());

        user.saveItem(item);
        itemList.add(item);
        assertEquals(itemList, user.getItemInventory());

        user.saveItem(item);
        assertEquals(itemList, user.getItemInventory());

        user.removeItem(item);
        itemList.remove(item);
        assertEquals(itemList, user.getItemInventory());
    }

    @Test
    void testSaveItemQuickMode() {
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        user.setActiveGameMode("QuickMode");
        user.unlockRoom(room);
        user.saveCurrentRoomID("room1");

        ArrayList<Item> itemList = new ArrayList<>();
        assertEquals(itemList, user.getItemInventory());

        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.saveItem(item);
        itemList.add(item);
        assertEquals(itemList, user.getItemInventory());

        user.saveItem(item);
        assertEquals(itemList, user.getItemInventory());

        user.removeItem(item);
        itemList.remove(item);
        assertEquals(itemList, user.getItemInventory());
    }

    @Test
    void testSaveItemTutorialMode() {
        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.setActiveGameMode("TutorialMode");

        ArrayList<Item> itemList = new ArrayList<>();
        assertEquals(itemList, user.getItemInventory());

        user.saveItem(item);
        itemList.add(item);
        assertEquals(itemList, user.getItemInventory());

        user.saveItem(item);
        assertEquals(itemList, user.getItemInventory());

        user.removeItem(item);
        itemList.remove(item);
        assertEquals(itemList, user.getItemInventory());
    }

    @Test
    void testSaveItemNoMode() {
        assertEquals(new ArrayList<>(), user.getItemInventory());

        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.saveItem(item);
        assertEquals(new ArrayList<>(), user.getItemInventory());

        assertFalse(user.removeItem(item));
    }

    @Test
    void testHasItemIDStoryMode() {
        assertFalse(user.hasItemID("1"));

        user.setActiveGameMode("StoryMode");
        assertFalse(user.hasItemID("1"));

        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.saveItem(item);
        assertTrue(user.hasItemID("1"));
        assertFalse(user.hasItemID("2"));

        Item item2 = new CommonItem("2", "item2", "test item 2", false, "fake_path_2");
        user.saveItem(item2);
        assertTrue(user.hasItemID("2"));
    }

    @Test
    void testHasItemIDQuickMode() {
        user.setActiveGameMode("QuickMode");
        user.saveCurrentRoomID("room1");
        assertFalse(user.hasItemID("item1"));

        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.saveItem(item);
        assertTrue(user.hasItemID("1"));
        assertFalse( user.hasItemID("2"));
    }

    @Test
    void testHasItemIDTutorialMode() {
        user.setActiveGameMode("TutorialMode");
        assertFalse( user.hasItemID("1"));

        Item item = new CommonItem("1", "item1", "test item 1", false, "fake_path");
        user.saveItem(item);
        assertTrue(user.hasItemID("1"));
        assertFalse(user.hasItemID("2"));
    }

    @Test
    void testSaveAndGetHints() {
        user.saveHint("object1", 2);
        assertEquals(new HashMap<>(), user.getHintsWatched());

        user.setActiveGameMode("StoryMode");
        user.saveHint("object1", 2);
        assertEquals(0, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        assertEquals(user.getStoryModeHintsWatched(), user.getHintsWatched());

        user.setActiveGameMode("QuickMode");
        user.saveCurrentRoomID("room1");
        user.saveHint("object1", 2);
        assertEquals(0, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        user.setActiveGameMode("TutorialMode");
        assertEquals(new HashMap<>(), user.getHintsWatched());
        user.saveHint("object1", 2);
        assertEquals(0, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        user.saveHint("object1", 2);
        assertEquals(1, user.getHintsWatched().get("object1"));

        Map<String, HashMap<String, Integer>> quickModehints = new HashMap<>();
        HashMap<String, Integer> hintsPerRoom = new HashMap<>();
        hintsPerRoom.put("object1", 1);
        quickModehints.put("room1", hintsPerRoom);
        assertEquals(quickModehints, user.getQuickModeHintsWatched());

    }

    @Test
    void testSaveAndGetStoryModeInteractables() {
        Interactable interactable = (Interactable) new CommonInteractable("1",
                "book",
                "A text book",
                "fake_default_sprite",
                "Open book",
                "An open text book",
                "fake_interacted_sprite",
                false, true, false,
                null, null, null, null,
                "Congrats");

        ArrayList<String> interactableList = new ArrayList<>();
        assertEquals(interactableList, user.getStoryModeInteractables());

        user.saveInteractable(interactable.getId());
        assertEquals(interactableList, user.getStoryModeInteractables());

        user.setActiveGameMode("StoryMode");
        user.saveInteractable(interactable.getId());
        interactableList.add(interactable.getId());
        assertEquals(interactableList, user.getStoryModeInteractables());
    }

    @Test
    void testSwitchRoomQuickMode() {
        user.setActiveGameMode("QuickMode");
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(),  new ArrayList<>(), new HashMap<>());

        assertFalse( user.getRoomsUnlocked().contains(room));
        user.switchRoom(room);
        assertNotEquals(room.getId(), user.getCurrentRoomID());
        assertFalse(user.getQuickModeHintsWatched().containsKey(room.getId()));

        user.unlockRoom(room);
        user.switchRoom(room);
        assertEquals(room.getId(), user.getCurrentRoomID());
        assertTrue(user.getQuickModeHintsWatched().containsKey(room.getId()));
        assertTrue(user.getQuickModeHintsWatched().get(room.getId()).isEmpty());
        assertNotNull(user.getItemInventory());
        assertTrue(user.getItemInventory().isEmpty());
    }

    @Test
    void testSwitchRoomStoryMode() {
        user.setActiveGameMode("StoryMode");
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path", new ArrayList<>(), new ArrayList<>(),  new ArrayList<>(), new HashMap<>());

        user.switchRoom(room);
        assertFalse(user.getRoomsUnlocked().contains(room));
        assertNotEquals(room.getId(), user.getCurrentRoomID());

        user.unlockRoom(room);
        user.switchRoom(room);
        assertEquals(room.getId(), user.getCurrentRoomID());
    }
}
