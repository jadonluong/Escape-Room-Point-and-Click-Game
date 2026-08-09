package application.use_cases.item.select_item;

public class SelectItemOutputData {
    private final String selectedItemId;

    public SelectItemOutputData(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public String getSelectedItemId() {
        return selectedItemId;
    }
}
