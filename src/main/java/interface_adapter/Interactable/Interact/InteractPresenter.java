package interface_adapter.Interactable.Interact;

import application.use_cases.Interactable.Interact.InteractOutputBoundary;
import application.use_cases.Interactable.Interact.InteractOutputData;
import interface_adapter.ViewManagerModel;
import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import view.ViewManager;

import java.util.List;

public class InteractPresenter implements InteractOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManager viewManager;

    public InteractPresenter(InteractViewModel interactViewModel, InventoryViewModel inventoryViewModel,
                             ViewManagerModel viewManagerModel, ViewManager viewManager) {
        this.interactViewModel = interactViewModel;
        this.inventoryViewModel = inventoryViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(InteractOutputData outputData) {
        InteractState interactState = interactViewModel.getState();
        interactState.setSuccessMessage(outputData.getSuccessMessage());
        interactState.setErrorMessage(null);

        interactViewModel.setState(interactState);
        interactViewModel.firePropertyChanged();

        if (outputData.getRewardItemName() != null) {
            InventoryState inventoryState = inventoryViewModel.getState();
            List<String> items = inventoryState.getItems();
            items.add(outputData.getRewardItemName());
            inventoryState.setItems(items);

            inventoryViewModel.setState(inventoryState);
            inventoryViewModel.firePropertyChanged();
        }

        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);

        interactViewModel.setState(state);
        interactViewModel.firePropertyChanged();

        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareRoomView(String roomId) {
        viewManagerModel.setState("in-game");
        viewManagerModel.firePropertyChanged();
    }
}
