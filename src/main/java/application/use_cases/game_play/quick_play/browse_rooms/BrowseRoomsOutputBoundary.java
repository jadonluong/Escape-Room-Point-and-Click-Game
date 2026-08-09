package application.use_cases.game_play.quick_play.browse_rooms;

public interface BrowseRoomsOutputBoundary {

    /**
     * Prepares the view to display the successfully retrieved room information.
     *
     * @param outputData the output data containing the available rooms
     */
    void prepareSuccessView(BrowseRoomsOutputData outputData);

    /**
     * Prepares the view to display a failure message when browsing rooms fails.
     *
     * @param message the error message describing the failure
     */
    void prepareFailView(String message);

}
