package application.use_cases.item.drop;

import domain.entities.item.Item;

public class DropInputData {
    private final Item item;

    public DropInputData(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
