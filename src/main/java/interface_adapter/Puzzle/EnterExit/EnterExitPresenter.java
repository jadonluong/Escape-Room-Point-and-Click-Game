package interface_adapter.Puzzle.EnterExit;

import application.use_cases.Puzzle.EnterExit.EnterExitOutputBoundary;
import application.use_cases.Puzzle.EnterExit.EnterExitOutputData;
import interface_adapter.Interactable.Interact.InteractState;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerModel;

public class EnterExitPresenter implements EnterExitOutputBoundary {
    private final EnterExitViewModel enterExitViewModel;
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;

    public EnterExitPresenter(EnterExitViewModel enterExitViewModel, InteractViewModel interactViewModel,
                              ViewManagerModel viewManagerModel) {
        this.enterExitViewModel = enterExitViewModel;
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareEnterView(EnterExitOutputData outputData) {
        EnterExitState state = enterExitViewModel.getState();
        state.setPuzzleType(outputData.getPuzzleType());
        state.setDescription(outputData.getDescription());
        state.setHint(outputData.getHint());
        state.setScrambled(outputData.getScrambled());
        state.setEncrypted(outputData.getEncrypted());
        state.setCipher(outputData.getCipher());

        viewManagerModel.setState("Puzzle");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareExitView() {
        viewManagerModel.setState("Zoom"); // No need to do anything since the current ZoomState is already correct!
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        InteractState state = interactViewModel.getState(); // Reuse the InteractOverlay for this :)
        state.setSuccessMessage(null);
        state.setErrorMessage(errorMessage);

        viewManagerModel.setState("Interact");
        viewManagerModel.firePropertyChanged();
    }
}
