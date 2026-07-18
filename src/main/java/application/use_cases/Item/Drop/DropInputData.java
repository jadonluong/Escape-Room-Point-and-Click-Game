package application.use_cases.Item.Drop;

import domain.entities.Item.Item;

public class DropInputData {
    private final Item item;

    public DropInputData(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
