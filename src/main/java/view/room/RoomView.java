package view.room;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import view.inventory.InventoryOverlay;
import interface_adapter.inventory.InventoryViewModel;

public class RoomView {

    private final StackPane mainStackPane;
    private final InventoryViewModel inventoryViewModel;

    // 1. Instance variable to keep track of the currently open overlay
    private InventoryOverlay activeInventoryOverlay = null;

    public RoomView(StackPane mainStackPane, InventoryViewModel inventoryViewModel) {
        this.mainStackPane = mainStackPane;
        this.inventoryViewModel = inventoryViewModel;
    }

    // 2. Define openInventory()
    private void openInventory() {
        // Create the overlay and pass 'this::closeInventory' as the onClose callback
        activeInventoryOverlay = new InventoryOverlay(this::closeInventory, inventoryViewModel);
        mainStackPane.getChildren().add(activeInventoryOverlay);
    }

    // 3. Define closeInventory()
    private void closeInventory() {
        if (activeInventoryOverlay != null) {
            mainStackPane.getChildren().remove(activeInventoryOverlay);
            activeInventoryOverlay = null; // Reset back to null when closed
        }
    }

    // 4. Register the key listener on your Scene
    public void registerKeyListeners(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.E) {
                if (activeInventoryOverlay == null) {
                    openInventory();
                } else {
                    closeInventory();
                }
                event.consume();
            }
        });
    }
}
