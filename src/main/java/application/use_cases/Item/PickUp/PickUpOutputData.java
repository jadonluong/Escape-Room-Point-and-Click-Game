package application.use_cases.Item.PickUp;

public class PickUpOutputData {
    private final String itemName;

    public PickUpOutputData(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return this.itemName;
    }
}
