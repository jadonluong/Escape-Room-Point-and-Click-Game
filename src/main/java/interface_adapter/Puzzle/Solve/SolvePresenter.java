package interface_adapter.Puzzle.Solve;

import application.use_cases.Puzzle.Solve.SolveOutputBoundary;
import application.use_cases.Puzzle.Solve.SolveOutputData;
import interface_adapter.Interactable.Interact.InteractState;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerModel;
import view.ViewManager;

public class SolvePresenter implements SolveOutputBoundary {
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManager viewManager;

    public SolvePresenter(InteractViewModel interactViewModel, ViewManagerModel viewManagerModel,
                          ViewManager viewManager) {
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(SolveOutputData outputData) {
        InteractState state = interactViewModel.getState();
        state.setSuccessMessage(outputData.getSuccessMessage());
        state.setErrorMessage(null);

        viewManagerModel.setState("Zoom"); // Bring back to ZoomView before opening InteractOverlay!
        viewManagerModel.firePropertyChanged();
        viewManager.showOverlay("Interact");
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState();
        state.setSuccessMessage(null);
        state.setErrorMessage(errorMessage);

        viewManager.showOverlay("Interact");
    }
}
