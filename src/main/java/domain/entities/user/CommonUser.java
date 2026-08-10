package domain.entities.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.item.Item;
import domain.entities.room.Room;

/**
 * The CommonUser class that extends the AbstractUser class and implements the CommonUserFunction interface.
 */
public class CommonUser extends AbstractUser implements CommonUserFunction {
    private final String username;
    private final String password;
    private final ModeProgress modeProgress;

    private final String storyModeString = "StoryMode";
    private final String quickModeString = "QuickMode";

    public CommonUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.modeProgress = new ModeProgress(this);
    }

    // Default constructor for Gson reflection
    public CommonUser() {
        super();
        this.username = "";
        this.password = "";
        this.modeProgress = new ModeProgress(this);
    }

    // ==========================================
    // NESTED CLASSES FOR GSON JSON FORMATTING
    // ==========================================

    public static class ModeProgress {
        private QuickModeData quickMode;
        private StoryModeData storyMode;

        public ModeProgress(CommonUser user) {
            this.quickMode = new QuickModeData(user);
            this.storyMode = new StoryModeData(user);
        }

        public QuickModeData getQuickMode() {
            return quickMode;
        }

        public StoryModeData getStoryMode() {
            return storyMode;
        }
    }

    public static class QuickModeData {
        private transient CommonUser user;
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

        public List<String> getQuickModeRoomsUnlocked() {
            return quickModeRoomsUnlocked;
        }

        public void setQuickModeRoomsUnlocked(List<String> quickModeRoomsUnlocked) {
            this.quickModeRoomsUnlocked = quickModeRoomsUnlocked;
        }

        public Map<String, ArrayList<String>> getQuickModeItemInventory() {
            return quickModeItemInventory;
        }

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
        private List<String> storyModeInteractables = new ArrayList<>();

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

        public void setStoryModeInteractables(List<String> storyModeInteractables) {
            this.storyModeInteractables = storyModeInteractables;
        }

        public List<String> getStoryModeInteractables() {
            return this.storyModeInteractables;
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
        if (room != null) {
            if (storyModeString.equalsIgnoreCase(this.activeGameMode)) {
                if (!getStoryModeRoomsUnlockedIds().contains(room.getId())) {
                    getStoryModeRoomsUnlockedIds().add(room.getId());
                }
            }
            else if (quickModeString.equalsIgnoreCase(this.activeGameMode)) {
                if (!getQuickModeRoomsUnlockedIds().contains(room.getId())) {
                    getQuickModeRoomsUnlockedIds().add(room.getId());
                }
            }
        }
    }

    @Override
    public void saveItem(Item item) {
        super.saveItem(item);

        if (storyModeString.equalsIgnoreCase(this.activeGameMode)) {
            if (!getStoryModeItemInventoryIds().contains(item.getId())) {
                getStoryModeItemInventoryIds().add(item.getId());
            }
        }
        else if (quickModeString.equalsIgnoreCase(this.activeGameMode)) {
            if (this.currentRoomID != null) {
                final ArrayList<String> itemIds = getQuickModeItemInventoryIds()
                        .computeIfAbsent(this.currentRoomID, string -> new ArrayList<>());
                if (!itemIds.contains(item.getId())) {
                    itemIds.add(item.getId());
                }
            }
        }
    }

    @Override
    public boolean removeItem(Item item) {
        final boolean removed = super.removeItem(item);

        if (removed) {
            // Sync String ID removal for Database persistence
            if (storyModeString.equalsIgnoreCase(this.activeGameMode)) {
                getStoryModeItemInventoryIds().remove(item.getId());
            }
            else if (quickModeString.equalsIgnoreCase(this.activeGameMode) && this.currentRoomID != null) {
                final ArrayList<String> itemIds = getQuickModeItemInventoryIds().get(this.currentRoomID);
                if (itemIds != null) {
                    itemIds.remove(item.getId());
                }
            }
        }
        return removed;
    }

    public ModeProgress getModeProgress() {
        return modeProgress;
    }

    /**
     * Handles switching rooms, maintaining per-room live and static item inventory and hints.
     * @param newRoom The ID of the room being entered.
     */
    @Override
    public void switchRoom(Room newRoom) {
        super.switchRoom(newRoom);
        if (quickModeString.equalsIgnoreCase(this.activeGameMode)) {
            // Ensure QuickMode JSON structures exist for the newly switched room
            if (this.currentRoomID != null) {
                getQuickModeItemInventoryIds().putIfAbsent(this.currentRoomID, new ArrayList<>());
            }
        }
    }

    @Override
    public void setQuickModeRoomsUnlockedIds(List<String> roomIDs) {
        modeProgress.getQuickMode().setQuickModeRoomsUnlocked(roomIDs);
    }

    @Override
    public void setQuickModeItemInventoryIds(Map<String, ArrayList<String>> itemIDs) {
        modeProgress.getQuickMode().setQuickModeItemInventory(itemIDs);
    }

    @Override
    public void setQuickModeHintsWatched(Map<String, HashMap<String, Integer>> hints) {
        // Updates AbstractUser live runtime field
        quickModeHintsWatched = hints;
        modeProgress.getQuickMode().setQuickModeHintsWatched(hints);
    }

    @Override
    public void setStoryModeRoomsUnlockedIds(List<String> roomIDs) {
        modeProgress.getStoryMode().setStoryModeRoomsUnlocked(roomIDs);
    }

    @Override
    public void setStoryModeItemInventoryIds(List<String> itemIDs) {
        modeProgress.getStoryMode().setStoryModeItemInventory(itemIDs);
    }

    @Override
    public void setStoryModeHintsWatched(HashMap<String, Integer> hints) {
        // Updates AbstractUser live runtime field
        storyModeHintsWatched = hints;
        modeProgress.getStoryMode().setStoryModeHintsWatched(hints);
    }

    @Override
    public List<String> getQuickModeRoomsUnlockedIds() {
        return modeProgress.getQuickMode().getQuickModeRoomsUnlocked();
    }

    @Override
    public Map<String, ArrayList<String>> getQuickModeItemInventoryIds() {
        return modeProgress.getQuickMode().getQuickModeItemInventory();
    }

    @Override
    public List<String> getStoryModeRoomsUnlockedIds() {
        return modeProgress.getStoryMode().getStoryModeRoomsUnlocked();
    }

    @Override
    public List<String> getStoryModeItemInventoryIds() {
        return modeProgress.getStoryMode().getStoryModeItemInventory();
    }

    @Override
    public void setStoryModeCurrentRoomID(String roomID) {
        modeProgress.getStoryMode().setStoryModeCurrentRoomID(roomID);
    }

    @Override
    public void saveInteractable(String interactableId) {
        super.saveInteractable(interactableId);

        if (storyModeString.equalsIgnoreCase(this.activeGameMode)) {
            if (this.getStoryModeInteractables() == null) {
                this.setStoryModeInteractables(new ArrayList<>());
            }
            if (!getStoryModeInteractables().contains(interactableId)) {
                getStoryModeInteractables().add(interactableId);
            }
        }
    }

    @Override
    public void saveCurrentRoomID(String roomID) {
        // Updates AbstractUser fields
        super.saveCurrentRoomID(roomID);

        if (storyModeString.equalsIgnoreCase(this.activeGameMode)) {
            // Updates ModeProgress field
            setStoryModeCurrentRoomID(roomID);
        }
    }

    @Override
    public String getStoryModeCurrentRoomID() {
        return modeProgress.getStoryMode().getStoryModeCurrentRoomID();
    }

    @Override
    public void setStoryModeInteractables(List<String> interactables) {
        modeProgress.getStoryMode().setStoryModeInteractables(interactables);
    }

    @Override
    public List<String> getStoryModeInteractables() {
        return modeProgress.getStoryMode().getStoryModeInteractables();
    }
}
