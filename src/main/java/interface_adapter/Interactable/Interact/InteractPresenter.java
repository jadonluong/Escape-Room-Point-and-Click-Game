package interface_adapter.Interactable.Interact;

import application.use_cases.Interactable.Interact.InteractOutputBoundary;
import application.use_cases.Interactable.Interact.InteractOutputData;
import interface_adapter.ViewManagerModel;

public class InteractPresenter implements InteractOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;

    public InteractPresenter(InteractViewModel interactViewModel, ViewManagerModel viewManagerModel) {
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(InteractOutputData outputData) {
        InteractState state = interactViewModel.getState();
        state.setSuccessMessage(outputData.getSuccessMessage());
        state.setErrorMessage(null);
        state.setReturnToView("Zoom");

        viewManagerModel.setState("Interact");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);
        state.setReturnToView("Zoom");

        viewManagerModel.setState("Interact");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareRoomView(String roomId) {
        viewManagerModel.setState("Room");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void preparePuzzleView(String puzzleId) {
        viewManagerModel.setState("Puzzle");
        viewManagerModel.firePropertyChanged();
    }
}
