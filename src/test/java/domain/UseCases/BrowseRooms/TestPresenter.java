package domain.UseCases.BrowseRooms;

import application.use_cases.game_play.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.game_play.QuickPlay.BrowseRooms.BrowseRoomsOutputData;

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
