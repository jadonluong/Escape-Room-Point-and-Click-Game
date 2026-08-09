package interface_adapter.game_play.browse_rooms;

import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsOutputBoundary;
import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsOutputData;
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

        viewModel.firePropertyChanged();

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String message) {
        BrowseRoomsState currentState = viewModel.getState();
        currentState.setErrorMessage(message);

        viewModel.firePropertyChanged();
    }
}
