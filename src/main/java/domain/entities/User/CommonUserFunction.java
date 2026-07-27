package domain.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface for getting the password of common user.
 */
public interface CommonUserFunction {

    /**
     * Returns the password of the common user.
     * @return the password of the common user.
     */
    String getPassword();

    /**
     * Sets the list of rooms (as their IDs) the user unlocked in quick mode.
     * @param roomIDs the IDs of the rooms the user unlocked
     */
    void setQuickModeRoomsUnlockedIDs(List<String> roomIDs);

    /**
     * Sets the list of items (as their IDs) the user collected in each unlocked room in quick mode.
     * @param itemIDs the list of items (as their IDs) the user collected in each unlocked room in quick mode
     */
    void setQuickModeItemInventoryIDs(Map<String, ArrayList<String>> itemIDs);

    /**
     * Sets the hints the user watched in each room in quick mode.
     * @param hints the hints the user watched in each room in quick mode
     */
    void setQuickModeHintsWatched(Map<String, HashMap<String, Integer>> hints);

    /**
     * Sets the list  of rooms (as their IDs) the user unlocked in story mode.
     * @param roomIDs the list  of rooms (as their IDs) the user unlocked in story mode
     */
    void setStoryModeRoomsUnlockedIDs(List<String> roomIDs);

    /**
     * Sets the list of items (as their IDs) the user collected in story mode.
     * @param itemIDs the IDs of the items the user collected
     */
    void setStoryModeItemInventoryIDs(List<String> itemIDs);

    /**
     * Sets the hints the user has watched in story mode.
     * @param hints the hints the user has watched (objectID -> request count)
     */
    void setStoryModeHintsWatched(HashMap<String, Integer> hints);

    /**
     * Returns the list of unlocked room IDs in quick mode.
     * @return the list of unlocked room IDs
     */
    List<String> getQuickModeRoomsUnlockedIDs();

    /**
     * Returns the list of collected item IDs of each unlocked room in quick mode.
     * @return the map of collected item IDs of each unlocked room
     */
    Map<String, ArrayList<String>> getQuickModeItemInventoryIDs();

    /**
     * Returns the list of unlocked room IDs in story mode.
     * @return the list of unlocked room IDs
     */
    List<String> getStoryModeRoomsUnlockedIDs();

    /**
     * Returns the list of item IDs in story mode.
     * @return the list of item IDs
     */
    List<String> getStoryModeItemInventoryIDs();

    /**
     * Saves the ID of the room the user is currently in as they play in story mode.
     * @param roomID the ID of the room
     */
    void setStoryModeCurrentRoomID(String roomID);

    /**
     * Returns the ID of the room the user is currently in as they play in story mode.
     * @return the ID of the room
     */
    String getStoryModeCurrentRoomID();

}
