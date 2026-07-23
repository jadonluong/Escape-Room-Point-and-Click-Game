package interface_adapter.inventory;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Inventory & Crafting overlay.
 * Inherits state management and PropertyChangeSupport from ViewModel<T>.
 */
public class InventoryViewModel extends ViewModel<InventoryState> {

    public static final String VIEW_NAME = "Inventory";

    public InventoryViewModel() {
        super(VIEW_NAME);
        // Initialize with a fresh, default state
        this.setState(new InventoryState());
    }
}