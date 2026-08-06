package application.use_cases.Item.PickUp;

public class PickUpInputData {
    private final String itemId;

    public PickUpInputData(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return this.itemId;
    }
}
