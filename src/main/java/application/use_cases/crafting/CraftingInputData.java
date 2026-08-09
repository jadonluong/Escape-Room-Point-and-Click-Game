package application.use_cases.crafting;

import domain.entities.item.Item;

/**
 * Input data containing the items to be used in the crafting operation.
 */
public class CraftingInputData {
    private final Item itemA;
    private final Item itemB;
    private final String itemAId;
    private final String itemBId;

    /**
     * Creates crafting input data with the two items to be combined.
     *
     * @param itemAId the first item to be used in crafting
     * @param itemBId the second item to be used in crafting
     */
    public CraftingInputData(String itemAId, String itemBId) {
        this.itemAId = itemAId;
        this.itemBId = itemBId;
        this.itemA = null;
        this.itemB = null;
    }

    /**
     * Constructor for Item entities.
     */
    public CraftingInputData(Item itemA, Item itemB) {
        this.itemA = itemA;
        this.itemB = itemB;
        this.itemAId = itemA != null ? itemA.getId() : null;
        this.itemBId = itemB != null ? itemB.getId() : null;
    }

    public String getItemAId() {
        return itemAId;
    }

    public String getItemBId() {
        return itemBId;
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
