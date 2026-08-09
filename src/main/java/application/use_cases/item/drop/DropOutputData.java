package application.use_cases.item.drop;

public class DropOutputData {
    private final String itemName;

    public DropOutputData(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return this.itemName;
    }
}
