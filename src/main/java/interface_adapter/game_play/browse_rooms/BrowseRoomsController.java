package interface_adapter.game_play.browse_rooms;

import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsInputBoundary;

public class BrowseRoomsController {
    private BrowseRoomsInputBoundary browseRoomsInteractor;

    public BrowseRoomsController(BrowseRoomsInputBoundary interactor) {
        this.browseRoomsInteractor = interactor;
    }

    public void execute() {
        browseRoomsInteractor.execute();
    }
}
