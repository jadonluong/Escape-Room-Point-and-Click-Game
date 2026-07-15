package domain.entities.User;

import domain.entities.Item.Item;

import java.util.ArrayList;

/**
 * The interface manages the user's item inventory.
 */
public interface UserInventory {

    /**
     * Returns the items this user collected.
     */
    ArrayList<Item> getItemInventory();

    /**
     * Saves the item to the user's item inventory.
     * @param item the item to be saved.
     */
    void saveItem(Item item);

    /**
     * Removes the item in the user's item inventory.
     * @param item the item to be removed.
     * @return true if item found in item inventory and false if not found.
     */
    boolean removeItem(Item item);

    /**
     * Saves the item the user has selected.
     * @param itemSelected
     */
    void saveSelectedItemID(String itemSelected);

    /**
     * Returns the item the user has selected.
     * @return the item the user selected.
     */
    String getSelectedItemID();
}
