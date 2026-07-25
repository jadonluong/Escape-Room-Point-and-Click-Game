package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

import domain.entities.Room.Room;

import java.util.List;

public class BrowseRoomsInteractor implements BrowseRoomsInputBoundary {
    private final BrowseRoomsDataAccessInterface dataAccess;
    private final BrowseRoomsOutputBoundary presenter;

    public BrowseRoomsInteractor(BrowseRoomsDataAccessInterface dataAccess, BrowseRoomsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;

    }

    @Override
    public void execute(BrowseRoomsInputData inputData) {
        try {
            // 1. Fetch domain entities via abstract interface
            List<Room> domainRooms = dataAccess.findRoomsByMode(inputData.getMode());

            if (domainRooms.isEmpty()) {
                presenter.prepareFailView("No open matches found for " + inputData.getMode());
                return;
            }

            // 3. CONVERT: Extract he IDs into a List of Strings
            List<String> roomIds = domainRooms.stream()
                    .map(Room::getId)
                    .toList();

            // 4. Instantiate Output Data class using the String IDs
            BrowseRoomsOutputData outputData = new BrowseRoomsOutputData(roomIds);

            // 5. Send the data back to the presenter
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            presenter.prepareFailView("An error occurred while fetching rooms: " + e.getMessage());
        }
    }
}
