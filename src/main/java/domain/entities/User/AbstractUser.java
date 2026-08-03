package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class that implements the User interface.
 */
public abstract class AbstractUser implements User {
    protected transient String activeGameMode;

    // The live game storage for quick mode
    protected transient ArrayList<Room> quickModeLiveRoomsUnlocked = new ArrayList<>();
    protected transient Map<String, ArrayList<Item>> quickModeLiveItemInventory = new HashMap<>();
    protected transient Map<String, HashMap<String, Integer>> quickModeHintsWatched = new HashMap<>();

    // The live game storage for story mode
    protected transient ArrayList<Room> storyModeLiveRoomsUnlocked = new ArrayList<>();
    protected transient ArrayList<Item> storyModeLiveItemInventory = new ArrayList<>();
    protected transient HashMap<String, Integer> storyModeHintsWatched = new HashMap<>();

    // The live game storage for tutorial mode
    protected transient ArrayList<Room> tutorialModeLiveRoomsUnlocked = new ArrayList<>();
    protected transient ArrayList<Item> tutorialModeLiveItemInventory = new ArrayList<>();
    protected transient HashMap<String, Integer> tutorialModeHintsWatched = new HashMap<>();

    protected transient String currentRoomID;
    protected transient String storyModeCurrentRoomID; // A copy of this is in common user which is saved in db.
    protected transient String selectedItemID;

    public AbstractUser() {
    }

    @Override
    public void setActiveGameMode(String activeGameMode) {
        this.activeGameMode = activeGameMode;
    }

    @Override
    public String getActiveGameMode() {
        return activeGameMode;
    }

    @Override
    public void unlockRoom(Room room) {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeLiveRoomsUnlocked.contains(room)) {
                storyModeLiveRoomsUnlocked.add(room);
            }
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!quickModeLiveRoomsUnlocked.contains(room)) {
                quickModeLiveRoomsUnlocked.add(room);
            }
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!tutorialModeLiveRoomsUnlocked.contains(room)) {
                tutorialModeLiveRoomsUnlocked.add(room);
            }
        }
    }

    @Override
    public ArrayList<Room> getRoomsUnlocked() {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.storyModeLiveRoomsUnlocked;
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.quickModeLiveRoomsUnlocked;
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.tutorialModeLiveRoomsUnlocked;
        }
        return new ArrayList<>();
    }

    @Override
    public void saveItem(Item item) {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeLiveItemInventory.contains(item)) {
                this.storyModeLiveItemInventory.add(item);
            }
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.currentRoomID != null) {
                ArrayList<Item> items = this.quickModeLiveItemInventory
                        .computeIfAbsent(this.currentRoomID, k -> new ArrayList<>());
                if (!items.contains(item)) {
                    items.add(item);
                }
            }
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.tutorialModeLiveItemInventory.contains(item)) {
                this.tutorialModeLiveItemInventory.add(item);
            }
        }
    }

    @Override
    public ArrayList<Item> getItemInventory() {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.storyModeLiveItemInventory == null) {
                this.storyModeLiveItemInventory = new ArrayList<>();
            }
            return this.storyModeLiveItemInventory;
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.currentRoomID != null) {
                // computeIfAbsent ensures a valid ArrayList is returned (never null)
                return this.quickModeLiveItemInventory
                        .computeIfAbsent(this.currentRoomID, k -> new ArrayList<>());
            }
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.tutorialModeLiveItemInventory == null) {
                this.tutorialModeLiveItemInventory = new ArrayList<>();
            }
            return this.tutorialModeLiveItemInventory;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean removeItem(Item item) {
        // If item is in itemInventory, remove it and return true.
        // If not found, leave the list alone and return false.
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.storyModeLiveItemInventory.remove(item);
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.quickModeLiveItemInventory.get(this.currentRoomID).remove(item);
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.tutorialModeLiveItemInventory.remove(item);
        }
        return false;
    }

    @Override
    public void saveCurrentRoomID(String roomID) {
        this.currentRoomID = roomID;

        if ("storyMode".equalsIgnoreCase(this.activeGameMode)) {
            this.storyModeCurrentRoomID = roomID;
        }
    }

    @Override
    public String getCurrentRoomID() {
        return this.currentRoomID;
    }

    @Override
    public String getStoryModeCurrentRoomID() {
        return this.storyModeCurrentRoomID;
    }

    @Override
    public void saveSelectedItemID(String itemID) {
        this.selectedItemID = itemID;
    }

    @Override
    public String getSelectedItemID() {
        return this.selectedItemID;
    }

    @Override
    public abstract String getUsername();

    @Override
    public abstract boolean isRegistered();

    @Override
    public boolean hasItemID(String itemID) {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeLiveItemInventory.isEmpty()) {
                for (Item item : this.storyModeLiveItemInventory) {
                    if (item.getId().equals(itemID)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.quickModeLiveItemInventory.isEmpty()) {
                for (Item item : this.quickModeLiveItemInventory.get(this.currentRoomID)) {
                    if (item.getId().equals(itemID)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.tutorialModeLiveItemInventory.isEmpty()) {
                for (Item item : this.tutorialModeLiveItemInventory) {
                    if (item.getId().equals(itemID)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public void saveHint(String objectID, int maxHintsAvailable) {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.storyModeHintsWatched.containsKey(objectID)) {
                this.storyModeHintsWatched.put(objectID, 0);
            }

            // maxHintsAvailable is the length of the list containing the hints written for the object with objectID,
            // so we need to cap at maxHintsAvailable - 1 instead of maxHintsAvailable.
            else {
                int currentHintIndex = this.storyModeHintsWatched.get(objectID);
                if (currentHintIndex < maxHintsAvailable - 1) {
                    this.storyModeHintsWatched.put(objectID, currentHintIndex + 1);
                }
            }
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            Map<String, Integer> roomHints = this.quickModeHintsWatched
                    .computeIfAbsent(this.currentRoomID, k -> new HashMap<>());

            if (!roomHints.containsKey(objectID)) {
                roomHints.put(objectID,  0);
            }

            else {
                int currentHintIndex = roomHints.get(objectID);
                if (currentHintIndex < maxHintsAvailable - 1) {
                    roomHints.put(objectID, currentHintIndex + 1);
                }
            }
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!this.tutorialModeHintsWatched.containsKey(objectID)) {
                this.tutorialModeHintsWatched.put(objectID, 0);
            }
            else {
                int currentHintIndex = this.tutorialModeHintsWatched.get(objectID);
                if (currentHintIndex < maxHintsAvailable - 1) {
                    this.tutorialModeHintsWatched.put(objectID, currentHintIndex + 1);
                }
            }
        }
    }

    /*This method returns the hints the user watched.
    If they are in story mode or tutorial mode: all the hints they've watched;
    If they are in quick mode, the hints they've watched in the room they are currently in.
     */
    @Override
    public HashMap<String, Integer> getHintsWatched() {
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.storyModeHintsWatched;
        }
        else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.quickModeHintsWatched.get(currentRoomID);
        }
        else if ("TutorialMode".equalsIgnoreCase(this.activeGameMode)) {
            return this.tutorialModeHintsWatched;
        }
        return new HashMap<>();
    }

    @Override
    public HashMap<String, Integer> getStoryModeHintsWatched() {
        return this.storyModeHintsWatched;
    }

    @Override
    public Map<String, HashMap<String, Integer>> getQuickModeHintsWatched() {
        return this.quickModeHintsWatched;
    }

    /**
     * Handles switching rooms, maintaining per-room live item inventory and hints.
     * @param newRoom The ID of the room being entered.
     */
    @Override
    public void switchRoom(Room newRoom) {
        if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            // Check if the room has been unlocked before switching
            if (this.quickModeLiveRoomsUnlocked.contains(newRoom)) {

                saveCurrentRoomID(newRoom.getId());

                // Handel item inventory and hint
                // If this room hasn't been visited in memory yet, initialize its item inventory and hintsWatched
                if (!this.quickModeLiveItemInventory.containsKey(newRoom.getId())) {
                    this.quickModeLiveItemInventory.put(newRoom.getId(), new ArrayList<>());
                    this.quickModeHintsWatched.put(newRoom.getId(), new HashMap<>());
                }
                // else newRoom has been visited and has item inventory and hints watched saved
                // so they can be accessed by getItemInventory and getHintsWatched
            }
        }

        // Check if the user can enter a room before switching
        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.storyModeLiveRoomsUnlocked.contains(newRoom)) {
                saveCurrentRoomID(newRoom.getId());
            }
        }
    }

    public void initializeRuntimeState() {
        if (this.quickModeLiveRoomsUnlocked == null) {
            this.quickModeLiveRoomsUnlocked = new ArrayList<>();
        }
        if (this.quickModeLiveItemInventory == null) {
            this.quickModeLiveItemInventory = new HashMap<>();
        }
        if (this.storyModeLiveRoomsUnlocked == null) {
            this.storyModeLiveRoomsUnlocked = new ArrayList<>();
        }
        if (this.storyModeLiveItemInventory == null) {
            this.storyModeLiveItemInventory = new ArrayList<>();
        }
    }
}
