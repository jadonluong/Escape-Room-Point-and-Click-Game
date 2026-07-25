package interface_adapter.GamePlay.BrowseRooms;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputData;
import interface_adapter.ViewManagerModel; // Optional: if using a ViewManager

public class BrowseRoomsPresenter implements BrowseRoomsOutputBoundary {
    private final BrowseRoomsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public BrowseRoomsPresenter(BrowseRoomsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(BrowseRoomsOutputData outputData) {
        BrowseRoomsState currentState = viewModel.getState();
        currentState.setRoomInfo(outputData.getInfo());
        currentState.setErrorMessage(null);

        viewModel.setState(currentState);
        viewModel.firePropertyChanged();

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String message) {
        BrowseRoomsState currentState = viewModel.getState();
        currentState.setErrorMessage(message);

        viewModel.setState(currentState);
        viewModel.firePropertyChanged();
    }
}
