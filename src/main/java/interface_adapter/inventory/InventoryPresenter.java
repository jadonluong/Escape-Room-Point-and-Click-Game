package interface_adapter.inventory;

import application.use_cases.GamePlay.ObjectsInfo;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import application.use_cases.Item.PickUp.PickUpOutputBoundary;
import application.use_cases.Item.PickUp.PickUpOutputData;

import application.use_cases.Item.PickUp.PickUpOutputBoundary;
import application.use_cases.Item.PickUp.PickUpOutputData;
import application.use_cases.Item.Drop.DropOutputBoundary;
import application.use_cases.Item.Drop.DropOutputData;
import application.use_cases.Crafting.CraftingOutputBoundary;
import application.use_cases.Crafting.CraftingOutputData;

import java.util.Map;

public class InventoryPresenter implements PickUpOutputBoundary, DropOutputBoundary, CraftingOutputBoundary {

    private final InventoryViewModel viewModel;
    private final InGameViewModel inGameViewModel;

    public InventoryPresenter(InventoryViewModel viewModel, InGameViewModel inGameViewModel) {
        this.viewModel = viewModel;
        this.inGameViewModel = inGameViewModel;
    }

    @Override
    public void prepareSuccessView(PickUpOutputData outputData) {
        // --- Update Inventory State ---
        InventoryState newState = new InventoryState(viewModel.getState());
        newState.getItems().add(outputData.getItemName());
        newState.setStatusMessage("Picked up: " + outputData.getItemName());

        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // 2. Remove Item
        if (inGameViewModel != null && inGameViewModel.getState() != null) {
            InGameState roomState = inGameViewModel.getState();

            if (roomState.getObjectsToDisplay() != null) {
                Map<String, ObjectsInfo> map = roomState.getObjectsToDisplay();

                // Try removing by item ID (e.g., "prison_item_1")
                map.remove(outputData.getItemId());

                // Try removing by item Name (e.g., "stick")
                map.remove(outputData.getItemName());

                // Fallback: Remove any key in the room matching ID or Name
                map.keySet().removeIf(key -> key.equalsIgnoreCase(outputData.getItemId())
                        || key.equalsIgnoreCase(outputData.getItemName()));
            }

            // 3. Trigger InGameView re-render so stick vanishes from floor
            inGameViewModel.firePropertyChanged();
        }
    }

    @Override
    public void prepareSuccessView(DropOutputData outputData) {
        InventoryState newState = new InventoryState(viewModel.getState());

        int indexToRemove = newState.getSelectedIndexA();
        if (indexToRemove >= 0 && indexToRemove < newState.getItems().size()) {
            String removed = newState.getItems().remove(indexToRemove);
            newState.setStatusMessage("Dropped: " + removed);
        }

        newState.setSelectedIndexA(-1);
        newState.setSelectedIndexB(-1);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(CraftingOutputData outputData) {
        InventoryState newState = new InventoryState(viewModel.getState());

        int idxA = newState.getSelectedIndexA();
        int idxB = newState.getSelectedIndexB();

        if (idxA != -1 && idxB != -1) {
            int maxIdx = Math.max(idxA, idxB);
            int minIdx = Math.min(idxA, idxB);

            if (maxIdx < newState.getItems().size()) newState.getItems().remove(maxIdx);
            if (minIdx < newState.getItems().size()) newState.getItems().remove(minIdx);
        }

        newState.getItems().add(outputData.getCraftedItemName());
        newState.setStatusMessage("Crafted: " + outputData.getCraftedItemName() + "!");

        newState.setSelectedIndexA(-1);
        newState.setSelectedIndexB(-1);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        InventoryState newState = new InventoryState(viewModel.getState());
        newState.setStatusMessage(error);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}
