package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * @param storyItemInventory the items the existing common user already collected in story mode
     * @param storyRooms the rooms the existing common user already unlocked in story mode
     * @param storyHints the hints the existing common user has watched in story mode
     * @param quickItemInventory the items the existing common user already collected in each room in quick mode
     * @param quickRooms the rooms the existing common user already unlocked in quick mode
     * @param quickHintsMap the hints the existing common user has watched in each room in quick mode
     * @return the restored common user.
     */
    User restoreCommonUser(String username,
                           String password,
                           // Story mode persistence
                           ArrayList<Item> storyItemInventory,
                           ArrayList<Room> storyRooms,
                           HashMap<String, Integer> storyHints,
                           // Quick mode persistence
                           Map<String, ArrayList<Item>> quickItemInventory,
                           ArrayList<Room> quickRooms,
                           Map<String, HashMap<String, Integer>> quickHintsMap);
}
