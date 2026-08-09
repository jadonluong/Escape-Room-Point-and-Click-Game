package domain.entities.user;

import java.util.ArrayList;

import domain.entities.item.Item;

/**
 * The interface manages the user's item inventory.
 */
public interface UserInventory {

    /**
     * Returns the items this user collected.
     * @return the item inventory of the user containing item objects
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
     * @param itemSelected the item the user has selected
     */
    void saveSelectedItemID(String itemSelected);

    /**
     * Returns the item the user has selected.
     * @return the item the user selected.
     */
    String getSelectedItemID();

    /**
     * Checks whether the user has an item with the given ID.
     * @param itemID the ID of the item to be looked for
     * @return true if the user has an item with itemID, false otherwise
     */
    boolean hasItemID(String itemID);
}
