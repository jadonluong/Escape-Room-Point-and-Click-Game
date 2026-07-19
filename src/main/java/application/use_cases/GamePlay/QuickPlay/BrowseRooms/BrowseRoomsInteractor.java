package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Room.Room;

import java.util.List;

//TODO: Modify after room repo is implemented.
public class BrowseRoomsInteractor implements BrowseRoomsInputBoundary {
    private final RoomRepository roomRepository;

    public BrowseRoomsInteractor(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void execute(BrowseRoomsInputData inputData, SelectModeOutputBoundary presenter) {
        try {
            // 1. Fetch domain entities via abstract interface
            List<Room> domainRooms = roomRepository.findRoomsByMode(inputData.getMode());

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
            presenter.prepareRoomsView(outputData);

        } catch (Exception e) {
            presenter.prepareFailView("An error occurred while fetching rooms: " + e.getMessage());
        }
    }
}
