package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * An abstract class that implements the User interface.
 */
public abstract class AbstractUser implements User {
    protected ArrayList<Item> itemInventory;
    protected ArrayList<Room> roomsUnlocked;
    protected String currentRoomID;
    protected String selectedItemID;

    public AbstractUser() {
        this.itemInventory = new ArrayList<>();
        this.roomsUnlocked = new ArrayList<>();
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
}
