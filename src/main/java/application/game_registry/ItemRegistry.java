package application.game_registry;

import domain.entities.Item.Item;

/**
 * The registry for getting Item objects with their itemIDs.
 */
public interface ItemRegistry {

    /**
     * Returns the Item object with the given ID.
     * @param itemID the item ID
     * @return the Item object with itemID
     */
    Item getItemById(String itemID);
}
