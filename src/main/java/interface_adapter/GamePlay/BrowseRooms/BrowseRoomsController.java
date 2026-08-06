package interface_adapter.GamePlay.BrowseRooms;

import application.use_cases.game_play.QuickPlay.BrowseRooms.BrowseRoomsInputBoundary;

public class BrowseRoomsController {
    private BrowseRoomsInputBoundary browseRoomsInteractor;

    public BrowseRoomsController(BrowseRoomsInputBoundary interactor) {
        this.browseRoomsInteractor = interactor;
    }

    public void execute() {
        browseRoomsInteractor.execute();
    }
}
