package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

public interface BrowseRoomsOutputBoundary {
    void prepareFailView(String message);
    void prepareSuccessView(BrowseRoomsOutputData outputData);
}
