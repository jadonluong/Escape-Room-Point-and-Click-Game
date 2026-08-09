package interface_adapter.inventory;

import application.use_cases.game_play.ObjectsInfo;
import interface_adapter.game_play.InGameState;
import interface_adapter.game_play.InGameViewModel;
import application.use_cases.item.pick_up.PickUpOutputBoundary;
import application.use_cases.item.pick_up.PickUpOutputData;

import application.use_cases.item.drop.DropOutputBoundary;
import application.use_cases.item.drop.DropOutputData;
import application.use_cases.crafting.CraftingOutputBoundary;
import application.use_cases.crafting.CraftingOutputData;

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
        // 1. Update Inventory State (using 'viewModel' and 'state.getItems()')
        InventoryState state = viewModel.getState();
        state.getItems().add(outputData.getItemId() + ":" + outputData.getItemName()); //Stores item ID into inventory list
        state.setStatusMessage("Picked up: " + outputData.getItemName());
        viewModel.firePropertyChanged();

        // 2. Remove item from the active room floor map
        if (inGameViewModel != null && inGameViewModel.getState() != null) {
            InGameState roomState = inGameViewModel.getState();

            if (roomState.getObjectsToDisplay() != null) {
                Map<String, ObjectsInfo> map = roomState.getObjectsToDisplay();
                map.remove(outputData.getItemId());
                map.remove(outputData.getItemName());
                map.keySet().removeIf(key -> key.equalsIgnoreCase(outputData.getItemId())
                        || key.equalsIgnoreCase(outputData.getItemName()));
            }

            // 3. Re-render the room
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

        newState.getItems().add(outputData.craftedItemName());
        newState.setStatusMessage("Crafted: " + outputData.craftedItemName() + "!");

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
