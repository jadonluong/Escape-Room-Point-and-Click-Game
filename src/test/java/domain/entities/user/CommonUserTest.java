package domain.entities.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.entities.Item.CommonItem;
import domain.entities.Item.Item;
import domain.entities.Room.CommonRoom;
import domain.entities.Room.Room;
import domain.entities.User.CommonUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUserTest extends  AbstractUserTest<CommonUser> {

    @Override
    protected CommonUser createUser() {
        return new CommonUser("test user", "password");
    }

    @Test
    void testGetUsername() {
        assertEquals("test user", user.getUsername());
    }

    @Test
    void testIsRegistered() {
        assertTrue(user.isRegistered());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    void testUnlockRoom_IDsAlsoRecorded() {
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        user.setActiveGameMode("StoryMode");
        user.unlockRoom(room);
        List<String> roomIdList = new ArrayList<>();
        roomIdList.add("room1");
        assertEquals(roomIdList, user.getStoryModeRoomsUnlockedIDs());

        user.unlockRoom(room);
        assertEquals(roomIdList, user.getStoryModeRoomsUnlockedIDs());

        user.setActiveGameMode("QuickMode");
        user.unlockRoom(room);
        assertEquals(roomIdList, user.getQuickModeRoomsUnlockedIDs());

        user.unlockRoom(room);
        assertEquals(roomIdList, user.getQuickModeRoomsUnlockedIDs());
    }

    @Test
    void testSaveAndRemoveItem() {
        Item item = new CommonItem("1", "item1", "test item 1",
                false, "fake_path");

        user.saveItem(item);
        assertTrue(user.getStoryModeItemInventoryIDs().isEmpty());
        assertTrue(user.getQuickModeItemInventoryIDs().isEmpty());

        user.setActiveGameMode("StoryMode");
        user.saveItem(item);
        assertTrue(user.getStoryModeItemInventoryIDs().contains("1"));

        user.removeItem(item);
        assertFalse(user.getStoryModeItemInventoryIDs().contains("1"));

        user.setActiveGameMode("QuickMode");
        user.saveCurrentRoomID("room1");
        user.saveItem(item);
        assertTrue(user.getQuickModeItemInventoryIDs().containsKey("room1"));
        assertTrue(user.getQuickModeItemInventoryIDs().get("room1").contains("1"));

        user.removeItem(item);
        assertTrue(user.getQuickModeItemInventoryIDs().containsKey("room1"));
        assertFalse(user.getQuickModeItemInventoryIDs().get("room1").contains("1"));
    }

    @Test
    void testSwitchRoomQuickModeInitialized() {
        user.setActiveGameMode("QuickMode");
        Room room = new CommonRoom("room1", "test room 1", "fake_room_path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        user.unlockRoom(room);
        assertTrue(user.getQuickModeItemInventoryIDs().isEmpty());
        user.switchRoom(room);
        assertTrue(user.getQuickModeItemInventoryIDs().containsKey(room.getId()));
        assertTrue(user.getQuickModeItemInventoryIDs().get(room.getId()).isEmpty());
    }

    @Test
    void testSetAndGetQuickModeRoomsUnlockedIDs() {
        List<String> roomsUnlockedIDs = new ArrayList<>();
        roomsUnlockedIDs.add("room1");
        user.setQuickModeRoomsUnlockedIDs(roomsUnlockedIDs);
        assertEquals(roomsUnlockedIDs, user.getQuickModeRoomsUnlockedIDs());
    }

    @Test
    void testSetAndGetQuickModeItemInventoryIDs() {
        Map<String, ArrayList<String>> itemIDMap = new HashMap<>();
        ArrayList<String> itemIDs = new ArrayList<>();
        itemIDs.add("1");
        itemIDMap.put("room1", itemIDs);
        user.setQuickModeItemInventoryIDs(itemIDMap);
        assertEquals(itemIDMap, user.getQuickModeItemInventoryIDs());
    }

    @Test
    void testSetAndGetQuickModeHintsWatched() {
        Map<String, HashMap<String, Integer>> hintsMap = new HashMap<>();
        HashMap<String, Integer> hints = new HashMap<>();
        hints.put("hint1", 1);
        hintsMap.put("room1", hints);
        user.setQuickModeHintsWatched(hintsMap);
        assertEquals(hintsMap, user.getQuickModeHintsWatched());
    }

    @Test
    void testSetAndGetStoryModeRoomsUnlockedIDs() {
        List<String> roomsUnlockedIDs = new ArrayList<>();
        roomsUnlockedIDs.add("room1");
        user.setStoryModeRoomsUnlockedIDs(roomsUnlockedIDs);
        assertEquals(roomsUnlockedIDs, user.getStoryModeRoomsUnlockedIDs());
    }

    @Test
    void testSetAndGetStoryModeItemInventoryIDs() {
        List<String> itemList = new ArrayList<>();
        itemList.add("item1");
        user.setStoryModeItemInventoryIDs(itemList);
        assertEquals(itemList, user.getStoryModeItemInventoryIDs());
    }

    @Test
    void testSetAndGetStoryModeHintsWatched() {
        HashMap<String, Integer> hints = new HashMap<>();
        hints.put("object1", 2);
        user.setStoryModeHintsWatched(hints);
        assertEquals(hints, user.getStoryModeHintsWatched());
    }

    @Test
    void testSetAndGetStoryModeCurrentRoomID() {
        user.setStoryModeCurrentRoomID("room1");
        assertEquals("room1", user.getStoryModeCurrentRoomID());
    }

    @Test
    void testGetModeProgress() {
        assertInstanceOf(CommonUser.ModeProgress.class, user.getModeProgress());
    }

    @Test
    void testCommonUserGsonRoundTrip() {
        // --- Populate QuickMode Data using public wrapper methods ---
        List<String> quickRooms = List.of("room1", "room2");
        this.user.setQuickModeRoomsUnlockedIDs(quickRooms);

        Map<String, ArrayList<String>> quickInventory = new HashMap<>();
        ArrayList<String> room1Items = new ArrayList<>(List.of("RustyKey", "Flashlight"));
        quickInventory.put("room1", room1Items);
        this.user.setQuickModeItemInventoryIDs(quickInventory);

        Map<String, HashMap<String, Integer>> quickHints = new HashMap<>();
        HashMap<String, Integer> room1Hints = new HashMap<>();
        room1Hints.put("safe_combination_hint", 1);
        quickHints.put("room1", room1Hints);
        this.user.setQuickModeHintsWatched(quickHints);

        // --- Populate StoryMode Data ---
        this.user.setStoryModeCurrentRoomID("room2");

        List<String> storyRooms = List.of("room2", "room3", "room4");
        this.user.setStoryModeRoomsUnlockedIDs(storyRooms);

        List<String> storyInventory = List.of("LabKeycard", "AcidVial");
        this.user.setStoryModeItemInventoryIDs(storyInventory);

        HashMap<String, Integer> storyHints = new HashMap<>();
        storyHints.put("laser_grid_puzzle", 3);
        this.user.setStoryModeHintsWatched(storyHints);

        // Setup Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // --- Serialization ---
        String jsonResult = gson.toJson(this.user);

        // Print out for debugging if needed during test execution
        System.out.println("Generated JSON:\n" + jsonResult);

        // --- Serialization Validation: Verify structural JSON format ---
        assertNotNull(jsonResult, "JSON output should not be null.");

        // Verify key matching boundaries for outer fields and inner components
        assertTrue(jsonResult.contains("\"username\": \"test user\""), "JSON missing username.");
        assertTrue(jsonResult.contains("\"password\": \"password\""), "JSON missing password.");
        assertTrue(jsonResult.contains("\"storyModeCurrentRoomID\": \"room2\""), "JSON missing active room tracking.");

        // Verify transient protection (Ensuring 'user' back-reference field is skipped by Gson)
        assertFalse(jsonResult.contains("\"activeGameMode\":"), "Transient parent object references leaked into nested JSON string!");

        // --- Deserialization: JSON String -> New Object ---
        CommonUser deserializedUser = gson.fromJson(jsonResult, CommonUser.class);

        // --- Deserialization Validation: Verify full data restoration ---
        assertNotNull(deserializedUser, "Deserialized user object should not be null.");

        // Check runtime/inherited synchronization fields
        assertNotNull(deserializedUser.getModeProgress(), "Nested ModeProgress object was not re-instantiated.");

        // Validate QuickMode field consistency
        assertEquals(quickRooms, deserializedUser.getQuickModeRoomsUnlockedIDs(),
                "QuickMode room lists mismatched after parsing.");
        assertEquals(quickInventory, deserializedUser.getQuickModeItemInventoryIDs(),
                "QuickMode room inventory mappings changed after parsing.");

        // Validate StoryMode field consistency
        assertEquals("room2", deserializedUser.getStoryModeCurrentRoomID(),
                "StoryMode room index identifier corrupted during extraction.");
        assertEquals(storyRooms, deserializedUser.getStoryModeRoomsUnlockedIDs(),
                "StoryMode room historical lists mismatched.");
        assertEquals(storyInventory, deserializedUser.getStoryModeItemInventoryIDs(),
                "StoryMode item item lists changed sequence or dropped items.");

        // AbstractUser's hints live field is populated in the JsonUserDataAccessObject
    }
}
