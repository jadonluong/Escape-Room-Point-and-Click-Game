package interface_adapter.GamePlay.ActionTrigger;

import application.use_cases.GamePlay.ActionTrigger.ActionTriggerOutPutBoundary;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;

public class ActionTriggerPresenter implements ActionTriggerOutPutBoundary {

    private final InGameViewModel viewModel;

    public ActionTriggerPresenter(InGameViewModel inGameViewModel) {
        this.viewModel = inGameViewModel;
    }

    @Override
    public void prepareFailView(String message) {
        // Update state with error details
        InGameState currentState = viewModel.getState();
        currentState.setErrorMessage(message);

        viewModel.firePropertyChanged();
    }
}
