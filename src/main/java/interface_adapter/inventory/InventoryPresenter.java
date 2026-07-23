package interface_adapter.inventory;

import application.use_cases.Item.PickUp.PickUpOutputBoundary;
import application.use_cases.Item.PickUp.PickUpOutputData;
import application.use_cases.Item.Drop.DropOutputBoundary;
import application.use_cases.Item.Drop.DropOutputData;
import application.use_cases.Crafting.CraftingOutputBoundary;
import application.use_cases.Crafting.CraftingOutputData;

public class InventoryPresenter implements PickUpOutputBoundary, DropOutputBoundary, CraftingOutputBoundary{
    private final InventoryViewModel viewModel;

    public InventoryPresenter(InventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    // ==========================================
    // 1. PickUp Use Case Callbacks
    // ==========================================
    @Override
    public void prepareSuccessView(PickUpOutputData outputData) {
        InventoryState newState = new InventoryState(viewModel.getState());

        String[] updatedItems = newState.getItems();
        // Insert item name into the first empty slot found
        for (int i = 0; i < updatedItems.length; i++) {
            if (updatedItems[i] == null) {
                updatedItems[i] = outputData.getItemName();
                break;
            }
        }

        newState.setItems(updatedItems);
        newState.setStatusMessage("Picked up: " + outputData.getItemName());

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    // ==========================================
    // 2. Drop Use Case Callbacks
    // ==========================================
    @Override
    public void prepareSuccessView(DropOutputData outputData) {
        InventoryState newState = new InventoryState(viewModel.getState());

        String[] updatedItems = newState.getItems();
        int slotToClear = newState.getSelectedSlotA();

        if (slotToClear >= 0 && slotToClear < updatedItems.length) {
            String droppedName = updatedItems[slotToClear];
            updatedItems[slotToClear] = null;
            newState.setStatusMessage("Dropped: " + (droppedName != null ? droppedName : "item"));
        }

        // Reset selections
        newState.setSelectedSlotA(-1);
        newState.setSelectedSlotB(-1);
        newState.setItems(updatedItems);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    // ==========================================
    // 3. Crafting Use Case Callbacks
    // ==========================================
    @Override
    public void prepareSuccessView(CraftingOutputData outputData) {
        InventoryState newState = new InventoryState(viewModel.getState());
        String[] updatedItems = newState.getItems();

        // Clear ingredient slots
        if (newState.getSelectedSlotA() != -1) updatedItems[newState.getSelectedSlotA()] = null;
        if (newState.getSelectedSlotB() != -1) updatedItems[newState.getSelectedSlotB()] = null;

        // Place new crafted item in the first available slot
        for (int i = 0; i < updatedItems.length; i++) {
            if (updatedItems[i] == null) {
                updatedItems[i] = outputData.getCraftedItemName();
                break;
            }
        }

        // Reset selections and update message
        newState.setSelectedSlotA(-1);
        newState.setSelectedSlotB(-1);
        newState.setItems(updatedItems);
        newState.setStatusMessage("Successfully crafted: " + outputData.getCraftedItemName() + "!");

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    // ==========================================
    // Shared Error Failure Callback
    // ==========================================
    @Override
    public void prepareFailView(String error) {
        InventoryState newState = new InventoryState(viewModel.getState());
        newState.setStatusMessage(error);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}
