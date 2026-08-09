package application.use_cases.item.pick_up;

import domain.entities.item.Item;

public class PickUpOutputData {
    private final String itemId;
    private final String itemName;

    public PickUpOutputData(String itemId, String itemName) {

        this.itemId = itemId;
        this.itemName = itemName;
    }

    public PickUpOutputData(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
    }

    public PickUpOutputData(String itemName) {
        this(itemName, itemName);
    }

    public String getItemName() {

        return this.itemName;
    }

    public String getItemId() {
        return this.itemId;
    }
}
