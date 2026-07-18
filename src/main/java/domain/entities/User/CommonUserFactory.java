package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * Factory for creating and restoring common users.
 */
public interface CommonUserFactory {

    /**
     * Creates a new common user.
     * @param username the username of the new common user.
     * @param password the password of the new common user.
     * @return the new common user.
     */
     User createCommonUser(String username, String password);

    /**
     * Restores a common user from database.
     * @param username the username of the existing common user.
     * @param password the password of the existing common user.
     * @param itemInventory the items the existing common user already collected.
     * @param rooms the rooms the existing common user already unlocked.
     * @return the restored common user.
     */
    User restoreCommonUser(String username, String password, ArrayList<Item> itemInventory, ArrayList<Room> rooms);
}
