package interface_adapter.interactable.interact;

import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.interactable.interact.InteractOutputBoundary;
import application.use_cases.interactable.interact.InteractOutputData;
import application.use_cases.interactable.zoom.ZoomInputData;
import application.use_cases.interactable.zoom.ZoomInteractor;
import interface_adapter.ViewManagerInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.game_play.InGameState;
import interface_adapter.game_play.InGameViewModel;
import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;

import java.util.List;
import java.util.Map;

public class InteractPresenter implements InteractOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final ZoomInteractor zoomInteractor;
    private final InGameViewModel inGameViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManagerInterface viewManager;

    public InteractPresenter(InteractViewModel interactViewModel, InventoryViewModel inventoryViewModel,
                             ZoomInteractor zoomInteractor, InGameViewModel inGameViewModel,
                             ViewManagerModel viewManagerModel, ViewManagerInterface viewManager) {
        this.interactViewModel = interactViewModel;
        this.inventoryViewModel = inventoryViewModel;
        this.zoomInteractor = zoomInteractor;
        this.inGameViewModel = inGameViewModel;
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

        InventoryState inventoryState = inventoryViewModel.getState();
        List<String> items = inventoryState.getItems();

        if (outputData.getRewardItemName() != null) {
            String rewardEntry = outputData.getRewardItemId() + ":" + outputData.getRewardItemName();
            items.add(rewardEntry);
        }

        if (outputData.getSelectedItemId() != null) {
            String consumedEntry = outputData.getSelectedItemId() + ":" + outputData.getSelectedItemName();
            items.remove(consumedEntry);
        }

        inventoryState.setItems(items);
        inventoryViewModel.setState(inventoryState);
        inventoryViewModel.firePropertyChanged();

        InGameState inGameState = inGameViewModel.getState();
        String interactableId = outputData.getInteractableId();
        Map<String, ObjectsInfo> objectsToDisplay = inGameState.getObjectsToDisplay();
        ObjectsInfo interactableInfo = objectsToDisplay.get(interactableId);
        ObjectsInfo newInteractableInfo = new ObjectsInfo(outputData.getInteractableSprite(),
                interactableInfo.position(), interactableInfo.type());
        objectsToDisplay.put(interactableId, newInteractableInfo);

        inGameState.setObjectsToDisplay(objectsToDisplay);
        inGameViewModel.setState(inGameState);
        inGameViewModel.firePropertyChanged();

        zoomInteractor.zoomIn(new ZoomInputData(outputData.getInteractableId()));

        if (outputData.getSuccessMessage() != null) {
            viewManager.showOverlay("Interact");
        }
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
    public void prepareRoomView(String imagePath, Map<String, ObjectsInfo> objectsToDisplay) {
        InGameState state = inGameViewModel.getState();
        state.setObjectsToDisplay(objectsToDisplay);
        state.setErrorMessage(null);
        state.setImgPath(imagePath);

        inGameViewModel.setState(state);
        inGameViewModel.firePropertyChanged();

        viewManagerModel.setState("in-game");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareMainMenuView() {
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();

        InteractState state = interactViewModel.getState();
        state.setErrorMessage(null);
        state.setSuccessMessage("Congratulations, you've escaped!");

        interactViewModel.setState(state);
        interactViewModel.firePropertyChanged();
        viewManager.showOverlay("Interact");
    }
}
