package application.use_cases.crafting;

import domain.entities.item.Item;

/**
 * Input data containing the items to be used in the crafting operation.
 */
public class CraftingInputData {
    private final Item itemA;
    private final Item itemB;

    /**
     * Creates crafting input data with the two items to be combined.
     *
     * @param itemA the first item to be used in crafting
     * @param itemB the second item to be used in crafting
     */
    public CraftingInputData(Item itemA, Item itemB) {
        this.itemA = itemA;
        this.itemB = itemB;
    }

    /**
     * Returns the first item to be used in crafting.
     *
     * @return the first crafting item
     */
    public Item getItemA() {
        return itemA;
    }

    /**
     * Returns the second item to be used in crafting.
     *
     * @return the second crafting item
     */
    public Item getItemB() {
        return itemB;
    }
}
