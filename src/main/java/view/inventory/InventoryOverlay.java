package view.inventory;

import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import interface_adapter.inventory.SelectItemController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.ViewManager;
import view.common.ModalOverlay;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class InventoryOverlay extends ModalOverlay implements PropertyChangeListener {

    private final InventoryViewModel viewModel;
    private final ViewManager viewManager;
    private final HBox hotbarContainer = new HBox(10);
    private final Label statusLabel = new Label();
    private final Button craftButton = new Button("Craft Selected");
    private final Button dropButton = new Button("Drop Selected");
    private SelectItemController selectItemController;

    public InventoryOverlay(ViewManager viewManager, InventoryViewModel viewModel) {
        super(() -> viewManager.hideOverlay("inventory"));
        this.viewManager = viewManager;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Force initial render of current state when overlay is created
        initialize();
        updateUI(viewModel.getState());
    }

    public void setSelectItemController(SelectItemController selectItemController) {
        this.selectItemController = selectItemController;
    }

    public void show() {
        // Re-sync UI state whenever opened
        updateUI(viewModel.getState());
        viewManager.showOverlay("inventory");
    }

    public void hide() {
        viewManager.hideOverlay("inventory");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateUI(viewModel.getState());
    }

    // Helper method to refresh the UI
    private void updateUI(InventoryState state) {
        hotbarContainer.getChildren().clear();

        List<String> items = state.getItems();
        if (items != null && !items.isEmpty()) {

            Map<String, Integer> itemCounts = new LinkedHashMap<>();
            for (String item : items) {
                itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
            }

            List<String> uniqueItems = new ArrayList<>(itemCounts.keySet());

            for (int i = 0; i < uniqueItems.size(); i++) {
                String rawItem = uniqueItems.get(i);
                int count = itemCounts.get(rawItem);

                String itemId = rawItem.contains(":") ? rawItem.split(":")[0] : rawItem;
                String displayName = rawItem.contains(":") ? rawItem.split(":")[1] : rawItem;

                Button slotButton = new Button(displayName + " x " + count);
                slotButton.setPrefSize(80, 80);
                slotButton.setMinSize(80, 80);

                if (i == state.getSelectedIndexA() || i == state.getSelectedIndexB()) {
                    slotButton.setStyle(
                            "-fx-background-color: #2c3e50; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-border-color: #f1c40f; " +
                                    "-fx-border-width: 3px; " +
                                    "-fx-border-radius: 5px;"
                    );
                } else {
                    slotButton.setStyle(
                            "-fx-background-color: #34495e; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-background-radius: 5px;"
                    );
                }

                int slotIndex = i;
                slotButton.setOnAction(e -> handleSlotClick(slotIndex, itemId));
                hotbarContainer.getChildren().add(slotButton);
            }
        }
        statusLabel.setText(state.getStatusMessage());
    }

    @Override
    protected VBox buildModalBox() {
        VBox mainLayout = new VBox(15);
        // mainLayout.setAlignment(Pos.BOTTOM_CENTER);
        mainLayout.setMaxWidth(650);
        mainLayout.setMaxHeight(350);

        StackPane.setAlignment(mainLayout, Pos.BOTTOM_CENTER);
        mainLayout.setStyle("-fx-background-color: rgba(20, 20, 25, 0.92); " +
                "-fx-padding: 25; " +
                "-fx-background-radius: 15; " +
                "-fx-border-color: #4a4a5a; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 15;"
        );

        statusLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");

        hotbarContainer.setAlignment(Pos.CENTER);
        hotbarContainer.setStyle("-fx-padding: 10;");

        ScrollPane scrollPane = new ScrollPane(hotbarContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(110);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox actionBox = new HBox(10, craftButton, dropButton);
        actionBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(statusLabel, scrollPane, actionBox);
        return mainLayout;
    }

    private void handleSlotClick(int slotIndex, String itemId) {
        InventoryState state = viewModel.getState();
        int idxA = state.getSelectedIndexA();
        int idxB = state.getSelectedIndexB();

        if (idxA == slotIndex && idxB == -1) {
            // 1. Clicked the currently selected item -> Deselect it
            state.setSelectedIndexA(-1);
            state.setSelectedIndexB(-1);
            selectItemController.execute(null);
        }
        else if (idxA != -1 && idxB != -1) {
            // 2. Both slots were active -> Collapse dual-selection back to ONLY this clicked item
            state.setSelectedIndexA(slotIndex);
            state.setSelectedIndexB(-1);
            selectItemController.execute(itemId);
        }
        else {
            // 3. Clicked a new item -> Immediately set as active item and clear slot B
            state.setSelectedIndexA(slotIndex);
            state.setSelectedIndexB(-1);
            selectItemController.execute(itemId);
        }

        // Refresh UI borders
        viewModel.firePropertyChanged();
    }
}