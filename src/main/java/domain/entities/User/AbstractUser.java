package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class that implements the User interface.
 */
public abstract class AbstractUser implements User {
    protected transient ArrayList<Item> itemInventory;
    protected transient ArrayList<Room> roomsUnlocked;
    protected transient String currentRoomID;
    protected transient String selectedItemID;
    protected HashMap<String, Integer> hintsWatched;

    public AbstractUser() {
        this.itemInventory = new ArrayList<>();
        this.roomsUnlocked = new ArrayList<>();
        this.hintsWatched = new HashMap<>();
    }

    @Override
    public void unlockRoom(Room room) {
        if (!this.roomsUnlocked.contains(room)) {
            this.roomsUnlocked.add(room);
        }
    }

    @Override
    public ArrayList<Room> getRoomsUnlocked() {
        return this.roomsUnlocked;
    }

    @Override
    public void saveItem(Item item) {
        if (!this.itemInventory.contains(item)) {
            this.itemInventory.add(item);
        }
    }

    @Override
    public ArrayList<Item> getItemInventory() {
        return this.itemInventory;
    }

    @Override
    public boolean removeItem(Item item) {
        // If item is in itemInventory, remove it and return true.
        // If not found, leave the list alone and return false.
        return this.itemInventory.remove(item);
    }

    @Override
    public void saveCurrentRoomID(String roomID) {
        this.currentRoomID = roomID;
    }

    @Override
    public String getCurrentRoomID() {
        return this.currentRoomID;
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
       for (Item item : this.itemInventory) {
           if (item.getId().equals(itemID)) {
               return true;
           }
       }
       return false;
    }

    @Override
    public void saveHint(String objectID, int maxHintsAvailable) {
        if (!this.hintsWatched.containsKey(objectID)) {
            this.hintsWatched.put(objectID, 0);
            return;
        }

        int currentHintIndex = this.hintsWatched.get(objectID);

        // maxHintsAvailable is the length of the list containing the hints written for the object with objectID,
        // so we need to cap at maxHintsAvailable - 1 instead of maxHintsAvailable.
        if (currentHintIndex < maxHintsAvailable - 1) {
            this.hintsWatched.put(objectID, currentHintIndex + 1);
        }
    }

    @Override
    public HashMap<String, Integer> getHintsWatched() {
        return this.hintsWatched;
    }

    @Override
    public void setHintProgress(HashMap<String, Integer> loadedHints) {
        if (loadedHints != null) {
            this.hintsWatched = new HashMap<>(loadedHints);
        }
    }
}
