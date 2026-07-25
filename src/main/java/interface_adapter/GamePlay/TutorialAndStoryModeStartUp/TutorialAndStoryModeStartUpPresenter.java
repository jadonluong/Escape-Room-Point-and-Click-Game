package interface_adapter.GamePlay.TutorialAndStoryModeStartUp;

import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpOutputBoundary;
import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpOutPutData;
import interface_adapter.ViewManagerModel;

public class TutorialAndStoryModeStartUpPresenter implements TutorialAndStoryModeStartUpOutputBoundary {
    private final TutorialAndStoryModeStartUpViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public TutorialAndStoryModeStartUpPresenter(TutorialAndStoryModeStartUpViewModel viewModel,
                                                ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData outputData) {
        // 1. Get current state and update it
        TutorialAndStoryModeStartUpState currentState = viewModel.getState();
        currentState.setObjectsToDisplay(outputData.getObjectToDisplay());
        currentState.setErrorMessage(null); // Clear error on success

        // 2. Notify ViewModel listeners
        viewModel.setState(currentState);
        viewModel.firePropertyChanged();

        // 3. Switch to the game view if a view manager is used

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String message) {
        // Update state with error details
        TutorialAndStoryModeStartUpState currentState = viewModel.getState();
        currentState.setErrorMessage(message);

        viewModel.setState(currentState);
        viewModel.firePropertyChanged();
    }
}

