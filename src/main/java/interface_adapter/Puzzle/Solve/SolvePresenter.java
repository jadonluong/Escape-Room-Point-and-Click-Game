package interface_adapter.Puzzle.Solve;

import application.use_cases.Puzzle.Solve.SolveOutputBoundary;
import application.use_cases.Puzzle.Solve.SolveOutputData;
import interface_adapter.Interactable.Interact.InteractState;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerModel;

public class SolvePresenter implements SolveOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;

    public SolvePresenter(InteractViewModel interactViewModel, ViewManagerModel viewManagerModel) {
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SolveOutputData outputData) {
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
        state.setSuccessMessage(null);
        state.setErrorMessage(errorMessage);
        state.setReturnToView("Puzzle");

        viewManagerModel.setState("Interact");
        viewManagerModel.firePropertyChanged();
    }
}
