package interface_adapter.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryState {
    private List<String> items = new ArrayList<>();
    private int selectedIndexA = -1;
    private int selectedIndexB = -1;
    private String statusMessage = "";

    public InventoryState() {}

    // Copy constructor (used by Presenters to create new updated state instances)
    public InventoryState(InventoryState copy) {
        this.items = new ArrayList<>(copy.items);
        this.selectedIndexA = copy.selectedIndexA;
        this.selectedIndexB = copy.selectedIndexB;
        this.statusMessage = copy.statusMessage;
    }
    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

    public int getSelectedIndexA() { return selectedIndexA; }
    public void setSelectedIndexA(int index) { this.selectedIndexA = index; }

    public int getSelectedIndexB() { return selectedIndexB; }
    public void setSelectedIndexB(int index) { this.selectedIndexB = index; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
