package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The CommonUser class that extends the AbstractUser class and implements the CommonUserFunction interface.
 */
public class CommonUser extends AbstractUser implements CommonUserFunction{
    private final String username;
    private final String password;

    // Quick Mode game state storage for JSON db (item and hint do not persist across rooms)
    private List<String> quickModeRoomsUnlocked = new ArrayList<>();
    private Map<String, ArrayList<String>> quickModeItemInventory = new HashMap<>();

    // Story Mode game state storage for JSON db(item and hints persist across rooms)
    private List<String> storyModeItemInventory = new ArrayList<>();
    private List<String> storyModeRoomsUnlocked = new ArrayList<>();

    public CommonUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void unlockRoom(Room room) {

        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeRoomsUnlocked.contains(room.getId())) {
                this.storyModeRoomsUnlocked.add(room.getId());
            }
            if (!this.storyModeLiveRoomsUnlocked.contains(room)) {
                storyModeLiveRoomsUnlocked.add(room);
            }
        }
        else {
            if (!quickModeRoomsUnlocked.contains(room.getId())) {
                quickModeRoomsUnlocked.add(room.getId());
            }
            if (!quickModeLiveRoomsUnlocked.contains(room)) {
                quickModeLiveRoomsUnlocked.add(room);
            }
        }
    }

    @Override
    public void saveItem(Item item) {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeLiveItemInventory.contains(item)) {
                this.storyModeLiveItemInventory.add(item);
            }
            if (!this.storyModeItemInventory.contains(item.getId())) {
                this.storyModeItemInventory.add(item.getId());
            }
        } else if (this.currentRoomID != null){
            ArrayList<Item> items = this.quickModeLiveItemInventory.get(this.currentRoomID);
            if (!items.contains(item)) {
                items.add(item);
            }

            ArrayList<String> itemIDs = this.quickModeItemInventory.get(this.currentRoomID);
            if (!itemIDs.contains(item.getId())) {
                itemIDs.add(item.getId());
            }
        }
    }

    @Override
    public boolean removeItem(Item item) {
        // If item is in itemInventory, remove it and return true.
        // If not found, leave the list alone and return false.
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.storyModeLiveItemInventory.remove(item) &&
                    this.storyModeItemInventory.remove(item.getId());
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.quickModeLiveItemInventory.get(this.currentRoomID).remove(item) &&
                    this.quickModeItemInventory.get(this.currentRoomID).remove(item.getId());
        }
        return false;
    }

    /**
     * Handles switching rooms, maintaining per-room live and static item inventory and hints.
     * @param newRoom The ID of the room being entered.
     */
    @Override
    public void switchRoom(Room newRoom) {
        if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            // Check if the room has been unlocked before switching. If the room is locked, the user cannot switch.
            if (this.quickModeLiveRoomsUnlocked.contains(newRoom)) {

                saveCurrentRoomID(newRoom.getId());

                // Handel item inventory and hint
                // If this room hasn't been visited in memory yet, initialize its item inventory and hintsWatched
                if (!this.quickModeLiveItemInventory.containsKey(newRoom.getId())) {
                    this.quickModeLiveItemInventory.put(newRoom.getId(), new ArrayList<>());
                    this.quickModeItemInventory.put(newRoom.getId(), new ArrayList<>());
                    this.quickModeHintsWatched.put(newRoom.getId(), new HashMap<>());
                }
                // else newRoom has been visited and has item inventory and hints watched saved
                // so they can be accessed by getItemInventory and getHintsWatched
            }
        }

        // Check if the user can enter a room before switching. If the room is locked, the user cannot switch.
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.storyModeLiveRoomsUnlocked.contains(newRoom)) {
                saveCurrentRoomID(newRoom.getId());
            }
        }
    }

    @Override
    public void setQuickModeRoomsUnlockedIDs(ArrayList<String> roomIDs) {
        this.quickModeRoomsUnlocked = roomIDs;
    }

    @Override
    public void setQuickModeItemInventoryIDs(HashMap<String, ArrayList<String>> itemIDs) {
        this.quickModeItemInventory = itemIDs;
    }

    @Override
    public void setQuickModeHintsWatched(Map<String, HashMap<String, Integer>> hints) {
        this.quickModeHintsWatched = hints;
    }

    @Override
    public void setStoryModeRoomsUnlockedIDs(ArrayList<String> roomIDs) {
        this.storyModeRoomsUnlocked = roomIDs;
    }

    @Override
    public void setStoryModeItemInventoryIDs(ArrayList<String> itemIDs) {
        this.storyModeItemInventory = itemIDs;
    }

    @Override
    public void setStoryModeHintsWatched(HashMap<String, Integer> hints) {
        this.storyModeHintsWatched = hints;
    }

    @Override
    public List<String> getQuickModeRoomsUnlockedIDs() {
        return this.quickModeRoomsUnlocked;
    }

    @Override
    public Map<String, ArrayList<String>> getQuickModeItemInventoryIDs() {
        return this.quickModeItemInventory;
    }

    @Override
    public List<String> getStoryModeRoomsUnlockedIDs() {
        return storyModeRoomsUnlocked;
    }

    @Override
    public List<String> getStoryModeItemInventoryIDs() {
        return storyModeItemInventory;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
