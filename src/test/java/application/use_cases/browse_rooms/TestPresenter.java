package application.use_cases.browse_rooms;

import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsOutputBoundary;
import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsOutputData;

public class TestPresenter implements BrowseRoomsOutputBoundary {

    private BrowseRoomsOutputData browseRoomsOutputData;
    private String errorMessage;

    @Override
    public void prepareFailView(String message) {
        this.errorMessage = message;
    }

    @Override
    public void prepareSuccessView(BrowseRoomsOutputData outputData) {
        this.browseRoomsOutputData = outputData;
    }

    public BrowseRoomsOutputData getSuccessData() {
        return browseRoomsOutputData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
