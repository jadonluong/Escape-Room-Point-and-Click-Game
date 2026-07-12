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

    public AbstractUser() {
        this.itemInventory = new ArrayList<>();
        this.roomsUnlocked = new ArrayList<>();
    }

    public void unlockRoom(Room room) {
        if (!this.roomsUnlocked.contains(room)) {
            this.roomsUnlocked.add(room);
        }
    };

    public ArrayList<Room> getRoomsUnlocked() {
        return this.roomsUnlocked;
    }

    public void saveItem(Item item) {
        if (!this.itemInventory.contains(item)) {
            this.itemInventory.add(item);
        }
    }

    public ArrayList<Item> getItemInventory() {
        return this.itemInventory;
    }

    public abstract boolean isRegistered();
}
