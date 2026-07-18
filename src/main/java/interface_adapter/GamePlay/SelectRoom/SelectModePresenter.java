package interface_adapter.GamePlay.SelectRoom;

import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;

public class SelectModePresenter implements SelectModeOutputBoundary {
    private final SelectModeViewModel viewModel;

    public SelectModePresenter(SelectModeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareGameStartView(String RoomId) {
        // Clear old errors and signal the view system to swap to the main gameplay layout
        viewModel.setErrorMessage("");
        viewModel.setActiveScreen("GAME_SCREEN");
    }

    @Override
    public void prepareFailView(String error) {
        // Feed the failure feedback directly into the UI state container
        viewModel.setErrorMessage(error);
    }
}
