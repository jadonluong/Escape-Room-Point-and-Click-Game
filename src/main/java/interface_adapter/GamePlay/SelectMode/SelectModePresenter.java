package interface_adapter.GamePlay.SelectMode;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputData;
import application.use_cases.GamePlay.SelectMode.SelectModeOutPutData;
import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import interface_adapter.ViewManagerModel;

public class SelectModePresenter implements SelectModeOutputBoundary {
    private final SelectModeViewModel selectModeViewModel;
    private final ViewManagerModel viewManagerModel;

    public SelectModePresenter(SelectModeViewModel selectModeViewModel, ViewManagerModel viewManagerModel) {
        this.selectModeViewModel = selectModeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareGameStartView(SelectModeOutPutData outputData) {
        // 1. Update SelectModeState with the current Room ID
        SelectModeState selectModeState = selectModeViewModel.getState();
        selectModeState.setRoomId(outputData.getRoomId());
        selectModeState.setErrorMessage(null);
        selectModeViewModel.setState(selectModeState);
        selectModeViewModel.firePropertyChanged();

        // 2. Switch active view using ViewManagerModel (adjust "GamePlayView" name as needed)

        viewManagerModel.setState("gamePlayView");
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String message) {

    }

}
