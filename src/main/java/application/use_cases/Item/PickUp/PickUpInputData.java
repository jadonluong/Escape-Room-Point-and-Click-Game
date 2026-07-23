package application.use_cases.Item.PickUp;

import domain.entities.Item.Item;

public class PickUpInputData {
    private final Item item;

    public PickUpInputData(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
