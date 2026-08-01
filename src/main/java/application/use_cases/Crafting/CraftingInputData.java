package application.use_cases.Crafting;

import domain.entities.Item.Item;

public class CraftingInputData {
    private final Item itemA;
    private final Item itemB;
    private final Item item;

    public CraftingInputData(Item itemA, Item itemB) {
        this.itemA = itemA;
        this.itemB = itemB;
    }

    public Item getItemA() {
        return this.itemA;
    }

    public Item getItemB() {
        return this.itemB;
    }

    public Item getItem() {
        return this.item;
    }
}
