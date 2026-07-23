package interface_adapter.inventory;

import java.util.Arrays;

public class InventoryState {
    private String[] items = new String[9]; // 9 slots for inventory items
    private boolean isInventoryOpen = false;
    private int selectedSlotA = -1; // Index of first item selected for crafting
    private int selectedSlotB = -1; // Index of second item selected for crafting
    private String statusMessage = "";

    public InventoryState() {}

    // Copy constructor (used by Presenters to create new updated state instances)
    public InventoryState(InventoryState copy) {
        this.items = Arrays.copyOf(copy.items, copy.items.length);
        this.isInventoryOpen = copy.isInventoryOpen;
        this.selectedSlotA = copy.selectedSlotA;
        this.selectedSlotB = copy.selectedSlotB;
        this.statusMessage = copy.statusMessage;
    }

    // --- Getters & Setters ---
    public boolean isInventoryOpen() { return isInventoryOpen; }
    public void setInventoryOpen(boolean open) { this.isInventoryOpen = open; }

    public String[] getItems() { return items; }
    public void setItems(String[] items) { this.items = items; }

    public int getSelectedSlotA() { return selectedSlotA; }
    public void setSelectedSlotA(int index) { this.selectedSlotA = index; }

    public int getSelectedSlotB() { return selectedSlotB; }
    public void setSelectedSlotB(int index) { this.selectedSlotB = index; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
