package interface_adapter.inventory;

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

        // --- Update InGame Room State (Fixes Problem A!) ---
        if (inGameViewModel != null && inGameViewModel.getState() != null) {
            InGameState roomState = inGameViewModel.getState();

            // Remove item from active room list if your state tracks items
            // roomState.removeRoomItem(outputData.getItemName());

            // Trigger room re-render
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
