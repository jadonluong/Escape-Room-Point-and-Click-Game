package interface_adapter.game_play.tutorial_and_story_mode_start_up;

import application.use_cases.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpOutPutData;
import application.use_cases.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpOutputBoundary;
import interface_adapter.game_play.InGameState;
import interface_adapter.game_play.InGameViewModel;
import interface_adapter.ViewManagerModel;

public class TutorialAndStoryModeStartUpPresenter implements TutorialAndStoryModeStartUpOutputBoundary {
    private final InGameViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public TutorialAndStoryModeStartUpPresenter(InGameViewModel viewModel,
                                                ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData outputData) {
        // 1. Get current state and update it
        final InGameState currentState = viewModel.getState();
        currentState.setObjectsToDisplay(outputData.getObjectToDisplay());
        currentState.setImgPath(outputData.getRoomImgPath());
        currentState.setErrorMessage(null);

        // 2. Notify ViewModel listeners
        viewModel.firePropertyChanged();

        // 3. Switch to the game view if a view manager is used

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String message) {
        // Update state with error details
        final InGameState currentState = viewModel.getState();
        currentState.setErrorMessage(message);

        viewModel.firePropertyChanged();
    }
}

