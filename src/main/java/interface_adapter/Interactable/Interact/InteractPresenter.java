package interface_adapter.Interactable.Interact;

import application.use_cases.Interactable.Interact.InteractOutputBoundary;
import application.use_cases.Interactable.Interact.InteractOutputData;
import interface_adapter.ViewManagerModel;
import view.ViewManager;

public class InteractPresenter implements InteractOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManager viewManager;

    public InteractPresenter(InteractViewModel interactViewModel, ViewManagerModel viewManagerModel,
                             ViewManager viewManager) {
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(InteractOutputData outputData) {
        InteractState state = interactViewModel.getState();
        state.setSuccessMessage(outputData.getSuccessMessage());
        state.setErrorMessage(null);

        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);

        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareRoomView(String roomId) {
        viewManagerModel.setState("Room");
        viewManagerModel.firePropertyChanged();
    }
}
