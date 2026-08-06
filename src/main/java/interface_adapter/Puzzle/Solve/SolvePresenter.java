package interface_adapter.Puzzle.Solve;

import application.use_cases.Interactable.Zoom.ZoomInputData;
import application.use_cases.Interactable.Zoom.ZoomInteractor;
import application.use_cases.Puzzle.Solve.SolveOutputBoundary;
import application.use_cases.Puzzle.Solve.SolveOutputData;
import interface_adapter.Interactable.Interact.InteractState;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;

import java.util.List;

public class
SolvePresenter implements SolveOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final ZoomInteractor zoomInteractor;
    private final ViewManagerModel viewManagerModel;
    private final ViewManagerInterface viewManager;

    public SolvePresenter(InteractViewModel interactViewModel, InventoryViewModel inventoryViewModel,
                          ZoomInteractor zoomInteractor, ViewManagerModel viewManagerModel, ViewManagerInterface viewManager) {
        this.interactViewModel = interactViewModel;
        this.inventoryViewModel = inventoryViewModel;
        this.zoomInteractor = zoomInteractor;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(SolveOutputData outputData) {
        InteractState interactState = interactViewModel.getState();
        interactState.setSuccessMessage(outputData.getSuccessMessage());
        interactState.setErrorMessage(null);

        interactViewModel.setState(interactState);
        interactViewModel.firePropertyChanged();

        if (outputData.getRewardItemName() != null) {
            InventoryState inventoryState = inventoryViewModel.getState();
            List<String> items  = inventoryState.getItems();
            items.add(outputData.getRewardItemId() + ":" + outputData.getRewardItemName());
            inventoryState.setItems(items);

            inventoryViewModel.setState(inventoryState);
            inventoryViewModel.firePropertyChanged();
        }

        zoomInteractor.zoomIn(new ZoomInputData(outputData.getInteractableId()));
        viewManagerModel.setState("Zoom"); // Bring back to ZoomView before opening InteractOverlay!
        viewManagerModel.firePropertyChanged();

        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState();
        state.setSuccessMessage(null);
        state.setErrorMessage(errorMessage);

        interactViewModel.setState(state);
        interactViewModel.firePropertyChanged();

        viewManager.showOverlay("Interact");
    }
}
