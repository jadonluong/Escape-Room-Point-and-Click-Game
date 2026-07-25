package view.inventory;

import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.common.ModalOverlay;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class InventoryOverlay extends ModalOverlay implements PropertyChangeListener {

    private final InventoryViewModel viewModel;
    private final HBox hotbarContainer = new HBox(10);
    private final Label statusLabel = new Label();
    private final Button craftButton = new Button("Craft Selected");
    private final Button dropButton = new Button("Drop Selected");

    public InventoryOverlay(Runnable onClose, InventoryViewModel viewModel) {
        super(onClose);
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    protected VBox buildModalBox() {
        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-padding: 20; -fx-background-radius: 12;");

        hotbarContainer.setAlignment(Pos.CENTER);
        hotbarContainer.setStyle("-fx-padding: 10;");

        // For horizontal scrolling
        ScrollPane scrollPane = new ScrollPane(hotbarContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(110);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox actionBox = new HBox(10, craftButton, dropButton);
        actionBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(statusLabel, scrollPane, actionBox);
        return mainLayout;
    }

    private void handleSlotClick(int index) {
        InventoryState state = viewModel.getState();
        if (state.getSelectedIndexA() == -1) {
            state.setSelectedIndexA(index);
        } else if (state.getSelectedIndexB() == -1 && index != state.getSelectedIndexA()) {
            state.setSelectedIndexB(index);
        } else {
            state.setSelectedIndexA(index);
            state.setSelectedIndexB(-1);
        }
        viewModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        InventoryState state = (InventoryState) evt.getNewValue();

        // 1. Clear previous hotbar buttons
        hotbarContainer.getChildren().clear();

        // 2. Dynamically rebuild hotbar buttons for every item in the ArrayList
        List<String> items = state.getItems();
        for (int i = 0; i < items.size(); i++) {
            String itemName = items.get(i);
            Button slotButton = new Button(itemName);
            slotButton.setPrefSize(80, 80);
            int slotIndex = i;
            slotButton.setOnAction(e -> handleSlotClick(slotIndex));

            hotbarContainer.getChildren().add(slotButton);
        }

        statusLabel.setText(state.getStatusMessage());
    }
}
