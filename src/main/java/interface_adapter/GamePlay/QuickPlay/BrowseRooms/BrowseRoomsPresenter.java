package interface_adapter.GamePlay.QuickPlay.BrowseRooms;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputData;

/**
 * The Presenter for the Browse Rooms Use Case.
 */
public class BrowseRoomsPresenter implements BrowseRoomsOutputBoundary {

    private final BrowseRoomsViewModel browseRoomsViewModel;

    public BrowseRoomsPresenter(BrowseRoomsViewModel browseRoomsViewModel) {
        this.browseRoomsViewModel = browseRoomsViewModel;
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
