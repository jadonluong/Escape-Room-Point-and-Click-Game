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

    private final ModeProgress ModeProgress;

    public CommonUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.ModeProgress = new ModeProgress(this);
    }

    // Default constructor for Gson reflection
    public CommonUser() {
        super();
        this.username = "";
        this.password = "";
        this.ModeProgress = new ModeProgress(this);
    }

    // ==========================================
    // NESTED CLASSES FOR GSON JSON FORMATTING
    // ==========================================

    public static class ModeProgress {
        private QuickModeData QuickMode;
        private StoryModeData StoryMode;

        public ModeProgress(CommonUser user) {
            this.QuickMode = new QuickModeData(user);
            this.StoryMode = new StoryModeData(user);
        }

        public QuickModeData getQuickMode() { return QuickMode; }
        public StoryModeData getStoryMode() { return StoryMode; }
    }

    public static class QuickModeData {
        private  transient CommonUser user;
        private List<String> quickModeRoomsUnlocked = new ArrayList<>();
        private Map<String, ArrayList<String>> quickModeItemInventory = new HashMap<>();
        private Map<String, HashMap<String, Integer>> quickModeHintsWatched;

        public QuickModeData(CommonUser user) {
            this.user = user;
            if (user != null) {
                this.quickModeHintsWatched = user.quickModeHintsWatched;
            }
            else {
                this.quickModeHintsWatched = new HashMap<>();
            }
        }

        public QuickModeData() {
            this.quickModeHintsWatched = new HashMap<>();
        }

        public List<String> getQuickModeRoomsUnlocked() { return quickModeRoomsUnlocked; }

        public void setQuickModeRoomsUnlocked(List<String> quickModeRoomsUnlocked) {
            this.quickModeRoomsUnlocked = quickModeRoomsUnlocked;
        }

        public Map<String, ArrayList<String>> getQuickModeItemInventory() { return quickModeItemInventory; }

        public void setQuickModeItemInventory(Map<String, ArrayList<String>> quickModeItemInventory) {
            this.quickModeItemInventory = quickModeItemInventory;
        }

        public Map<String, HashMap<String, Integer>> getQuickModeHintsWatched() {
            return quickModeHintsWatched;
        }

        public void setQuickModeHintsWatched(Map<String, HashMap<String, Integer>> hints) {
            quickModeHintsWatched = hints;
        }
    }

    public static class StoryModeData {
        private transient CommonUser user;
        private String storyModeCurrentRoomID;
        private List<String> storyModeRoomsUnlocked = new ArrayList<>();
        private List<String> storyModeItemInventory = new ArrayList<>();
        private HashMap<String, Integer> storyModeHintsWatched;

        public StoryModeData(CommonUser user) {
            this.user = user;
            if (user != null) {
                this.storyModeHintsWatched = user.storyModeHintsWatched;
            }
            else {
                this.storyModeHintsWatched = new HashMap<>();
            }
        }

        public StoryModeData() {
            this.storyModeHintsWatched = new HashMap<>();
        }

        public List<String> getStoryModeRoomsUnlocked() {
            return storyModeRoomsUnlocked;
        }

        public void setStoryModeRoomsUnlocked(List<String> storyModeRoomsUnlocked) {
            this.storyModeRoomsUnlocked = storyModeRoomsUnlocked;
        }

        public List<String> getStoryModeItemInventory() {
            return storyModeItemInventory;
        }

        public void setStoryModeItemInventory(List<String> storyModeItemInventory) {
            this.storyModeItemInventory = storyModeItemInventory;
        }

        public HashMap<String, Integer> getStoryModeHintsWatched() {
            return storyModeHintsWatched;
        }

        public void setStoryModeHintsWatched(HashMap<String, Integer> hints) {
            this.storyModeHintsWatched = hints;
        }

        public String getStoryModeCurrentRoomID() {
            return storyModeCurrentRoomID;
        }

        public void setStoryModeCurrentRoomID(String roomID) {
            this.storyModeCurrentRoomID = roomID;
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }

    @Override
    public void unlockRoom(Room room) {
        super.unlockRoom(room);

        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!getStoryModeRoomsUnlockedIDs().contains(room.getId())) {
                getStoryModeRoomsUnlockedIDs().add(room.getId());
            }
        } else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!getQuickModeRoomsUnlockedIDs().contains(room.getId())) {
                getQuickModeRoomsUnlockedIDs().add(room.getId());
            }
        }
    }

    @Override
    public void saveItem(Item item) {
        super.saveItem(item);

        if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
            if (!getStoryModeItemInventoryIDs().contains(item.getId())) {
                getStoryModeItemInventoryIDs().add(item.getId());
            }
        } else if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            if (this.currentRoomID != null){
                ArrayList<String> itemIDs = getQuickModeItemInventoryIDs()
                        .computeIfAbsent(this.currentRoomID, k -> new ArrayList<>());
                if (!itemIDs.contains(item.getId())) {
                    itemIDs.add(item.getId());
                }
            }
        }
    }

    @Override
    public boolean removeItem(Item item) {
        boolean removed = super.removeItem(item);

        if (removed) {
            // Sync String ID removal for Database persistence
            if ("StoryMode".equalsIgnoreCase(this.activeGameMode)) {
                getStoryModeItemInventoryIDs().remove(item.getId());
            } else if ("QuickMode".equalsIgnoreCase(this.activeGameMode) && this.currentRoomID != null) {
                ArrayList<String> itemIDs = getQuickModeItemInventoryIDs().get(this.currentRoomID);
                if (itemIDs != null) {
                    itemIDs.remove(item.getId());
                }
            }
        }
        return removed;
    }

    /**
     * Handles switching rooms, maintaining per-room live and static item inventory and hints.
     * @param newRoom The ID of the room being entered.
     */
    @Override
    public void switchRoom(Room newRoom) {
        super.switchRoom(newRoom);
        if ("QuickMode".equalsIgnoreCase(this.activeGameMode)) {
            // Ensure QuickMode JSON structures exist for the newly switched room
            if (this.currentRoomID != null) {
                getQuickModeItemInventoryIDs().putIfAbsent(this.currentRoomID, new ArrayList<>());
            }
        }
    }

    public ModeProgress getModeProgress() {
        return ModeProgress;
    }

    @Override
    public void setQuickModeRoomsUnlockedIDs(List<String> roomIDs) {
            ModeProgress.getQuickMode().setQuickModeRoomsUnlocked(roomIDs);
    }

    @Override
    public void setQuickModeItemInventoryIDs(Map<String, ArrayList<String>> itemIDs) {
        ModeProgress.getQuickMode().setQuickModeItemInventory(itemIDs);
    }

    @Override
    public void setQuickModeHintsWatched(Map<String, HashMap<String, Integer>> hints) {
        quickModeHintsWatched = hints; // Updates AbstractUser live runtime field
        ModeProgress.getQuickMode().setQuickModeHintsWatched(hints);
    }

    @Override
    public void setStoryModeRoomsUnlockedIDs(List<String> roomIDs) {
        ModeProgress.getStoryMode().setStoryModeRoomsUnlocked(roomIDs);
    }

    @Override
    public void setStoryModeItemInventoryIDs(List<String> itemIDs) {
        ModeProgress.getStoryMode().setStoryModeItemInventory(itemIDs);
    }

    @Override
    public void setStoryModeHintsWatched(HashMap<String, Integer> hints) {
        storyModeHintsWatched = hints; // Updates AbstractUser live runtime field
        ModeProgress.getStoryMode().setStoryModeHintsWatched(hints);
    }

    @Override
    public List<String> getQuickModeRoomsUnlockedIDs() {
        return ModeProgress.getQuickMode().getQuickModeRoomsUnlocked();
    }

    @Override
    public Map<String, ArrayList<String>> getQuickModeItemInventoryIDs() {
        return ModeProgress.getQuickMode().getQuickModeItemInventory();
    }

    @Override
    public List<String> getStoryModeRoomsUnlockedIDs() {
        return ModeProgress.getStoryMode().getStoryModeRoomsUnlocked();
    }

    @Override
    public List<String> getStoryModeItemInventoryIDs() {
        return ModeProgress.getStoryMode().getStoryModeItemInventory();
    }

    @Override
    public void setStoryModeCurrentRoomID(String roomID) {
        ModeProgress.getStoryMode().setStoryModeCurrentRoomID(roomID);
    }

    @Override
    public String getStoryModeCurrentRoomID() {
        return ModeProgress.getStoryMode().getStoryModeCurrentRoomID();
    }
}
