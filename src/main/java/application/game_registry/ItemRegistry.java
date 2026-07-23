package application.game_registry;

import domain.entities.Item.Item;

/**
 * The registry for populating itemInventory with Item objects when restoring a user.
 */
public interface ItemRegistry {

    /**
     * Returns the Item object with the given ID.
     * @param itemID the item ID
     * @return the Item object with itemID
     */
    Item getItemByID(String itemID);
}
