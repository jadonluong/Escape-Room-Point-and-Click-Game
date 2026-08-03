package application.use_cases.Item.SelectItem;

public class SelectItemInputData {
    private final String itemId;

    public SelectItemInputData(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
