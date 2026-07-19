package interface_adapter.GamePlay.SelectMode;

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
        //TODO: waiting to be fill out the detail.
    }

    @Override
    public void prepareSelectRoomView() {
        viewModel.setErrorMessage("");
        //TODO: waiting to be fill out the detail.
    }
}
