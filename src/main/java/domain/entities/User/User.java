package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * The representation of a user in the program.
 */
public interface User {

    /**
     * Returns the items this user collected.
     */
    ArrayList<Item> getItemInventory();

    /**
     * Returns the rooms the user has unlocked.
     */
    ArrayList<Room> getRoomsUnlocked();

    /**
     * Saves the item to the user's item inventory.
     * @param item the item to be saved.
     */
    void saveItem(Item item);

    /**
     * Saves the newly unclocked room to the user's unlocked room inventory.
     * @param room the newly unlocked room.
     */
    void unlockRoom(Room room);

    /**
     * Returns the type of user this user belongs to.
     * @return true if the user is a common user, false if the user is a guest user.
     */
    boolean isRegistered();
}
