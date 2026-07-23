package view.inventory;

import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.common.ModalOverlay;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InventoryOverlay extends ModalOverlay implements PropertyChangeListener {

    private final InventoryViewModel viewModel;
    private final Button[] itemButtons = new Button[9];
    private final Label statusLabel = new Label();
    private final Button craftButton = new Button("Craft Selected");
    private final Button dropButton = new Button("Drop Selected");

    public InventoryOverlay(Runnable onClose, InventoryViewModel viewModel) {
        super(onClose); // Satisfies Error #2 (passes onClose to parent constructor)
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
    }

    /**
     * Satisfies Error #1: Implements the abstract method required by ModalOverlay.
     */
    @Override
    protected VBox buildModalBox() {
        VBox contentContainer = new VBox(15);
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setStyle("-fx-background-color: rgba(30, 30, 30, 0.95); -fx-padding: 20; -fx-background-radius: 10;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {
            itemButtons[i] = new Button("Empty"); // Changed from JButton to Button
            itemButtons[i].setPrefSize(90, 90);
            int slotIndex = i;
            itemButtons[i].setOnAction(e -> handleSlotClick(slotIndex));

            int row = i / 3;
            int col = i % 3;
            grid.add(itemButtons[i], col, row);
        }

        contentContainer.getChildren().addAll(
                statusLabel,
                grid,
                craftButton,
                dropButton
        );

        return contentContainer;
    }

    private void handleSlotClick(int slotIndex) {
        InventoryState state = viewModel.getState();
        if (state.getSelectedSlotA() == -1) {
            state.setSelectedSlotA(slotIndex);
        } else if (state.getSelectedSlotB() == -1 && slotIndex != state.getSelectedSlotA()) {
            state.setSelectedSlotB(slotIndex);
        } else {
            state.setSelectedSlotA(slotIndex);
            state.setSelectedSlotB(-1);
        }
        viewModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        InventoryState state = (InventoryState) evt.getNewValue();

        // Refresh UI buttons with item names
        String[] items = state.getItems();
        for (int i = 0; i < 9; i++) {
            String name = (items != null && items[i] != null) ? items[i] : "Empty";
            if (i == state.getSelectedSlotA() || i == state.getSelectedSlotB()) {
                itemButtons[i].setText("[" + name + "]");
            } else {
                itemButtons[i].setText(name);
            }
        }

        statusLabel.setText(state.getStatusMessage());
    }
}
