package interface_adapter.GamePlay.QuickModeStartUp;

import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputBoundary;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputData;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import interface_adapter.ViewManagerModel;

public class QuickModeStartUpPresenter implements QuickModeStartUpOutputBoundary {
    private final InGameViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public QuickModeStartUpPresenter(InGameViewModel viewModel,
                                     ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareGameStartView(QuickModeStartUpOutputData outputData) {
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
