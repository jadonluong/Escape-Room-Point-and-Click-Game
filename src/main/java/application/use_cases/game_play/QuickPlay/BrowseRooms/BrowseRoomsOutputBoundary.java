package application.use_cases.game_play.QuickPlay.BrowseRooms;

public interface BrowseRoomsOutputBoundary {
    void prepareFailView(String message);
    void prepareSuccessView(BrowseRoomsOutputData outputData);
}
