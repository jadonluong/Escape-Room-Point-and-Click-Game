package application.use_cases.Item.SelectItem;

public class SelectItemOutputData {
    private final String selectedItemId;

    public SelectItemOutputData(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public String getSelectedItemId() {
        return selectedItemId;
    }
}
