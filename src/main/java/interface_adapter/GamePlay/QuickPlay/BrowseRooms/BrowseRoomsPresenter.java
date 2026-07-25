package interface_adapter.GamePlay.QuickPlay.BrowseRooms;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputData;
import interface_adapter.ViewManagerModel;

/**
 * The Presenter for the Browse Rooms Use Case.
 */
public class BrowseRoomsPresenter implements BrowseRoomsOutputBoundary {

    private final BrowseRoomsViewModel browseRoomsViewModel;
    private final ViewManagerModel viewManagerModel;

    public BrowseRoomsPresenter(BrowseRoomsViewModel browseRoomsViewModel, ViewManagerModel viewManagerModel) {
        this.browseRoomsViewModel = browseRoomsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(BrowseRoomsOutputData outputData) {
        // 1. Retrieve current state
        final BrowseRoomsState state = browseRoomsViewModel.getState();

        // 2. Update state with room IDs and clear any previous error
        state.setRoomIds(outputData.getIds());
        state.setError(null);

        // 3. Notify the View of the updated state
        this.browseRoomsViewModel.setState(state);
        this.browseRoomsViewModel.firePropertyChanged();

        // 4. Switch the view using ViewManagerModel
        this.viewManagerModel.setState(this.browseRoomsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String message) {
        // 1. Retrieve current state
        final BrowseRoomsState state = browseRoomsViewModel.getState();

        // 2. Set error message in state
        state.setError(message);

        // 3. Notify the View of the error state
        this.browseRoomsViewModel.setState(state);
        this.browseRoomsViewModel.firePropertyChanged();
    }
}
