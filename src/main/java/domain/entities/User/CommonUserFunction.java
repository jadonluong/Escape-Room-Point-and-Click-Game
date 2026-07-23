package domain.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

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
     * Sets the list of room IDs for data saving.
     * @param roomIDs the IDs of the rooms the user unlocked
     */
    void setRoomsUnlockedIDs(ArrayList<String> roomIDs);

    /**
     * Sets the list of item IDs for data saving.
     * @param itemIDs the IDs of the items the user collected
     */
    void setItemInventoryIDs(ArrayList<String> itemIDs);

    /**
     * Sets the hints the user has watched.
     * @param hints the hints the user has watched (objectID -> request count)
     */
    void setHintsWatched(HashMap<String, Integer> hints);

    /**
     * Returns the list of unlocked room IDs.
     * @return the list of unlocked room IDs
     */
    ArrayList<String> getRoomsUnlockedIDs();

    /**
     * Returns the list of collected item IDs.
     * @return the list of collected item IDs
     */
    ArrayList<String> getItemInventoryIDs();

    /**
     * Returns the hints the user has watched.
     * @return the hints the user has watched (objectID -> request count)
     */
    HashMap<String, Integer> getHintsWatched();
}
