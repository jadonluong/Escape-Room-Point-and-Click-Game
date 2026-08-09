package application.use_cases.item.select_item;

public class SelectItemInputData {
    private final String itemId;

    public SelectItemInputData(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
