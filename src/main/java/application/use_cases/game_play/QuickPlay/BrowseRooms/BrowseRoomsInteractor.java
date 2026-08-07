package application.use_cases.game_play.QuickPlay.BrowseRooms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Room.Room;

public class BrowseRoomsInteractor implements BrowseRoomsInputBoundary {
    private final BrowseRoomsDataAccessInterface dataAccess;
    private final BrowseRoomsOutputBoundary presenter;

    public BrowseRoomsInteractor(BrowseRoomsDataAccessInterface dataAccess, BrowseRoomsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;

    }

    @Override
    public void execute() {
        // Fetch domain entities via abstract interface
        final List<Room> rooms = dataAccess.getRoomsForQuickMode();

        if (rooms.isEmpty()) {
            presenter.prepareFailView("No rooms found");
            return;
        }

        // Put RoomId as key, description and img path as value.
        final Map<String, RoomInfo> data = new HashMap<>();
        rooms.forEach(room -> {
            data.put(room.getId(), new RoomInfo(room.getDescription(), room.getImagePath()));
        });

        // Instantiate Output Data class using the String IDs
        final BrowseRoomsOutputData outputData = new BrowseRoomsOutputData(data);

        // Send the data back to the presenter
        presenter.prepareSuccessView(outputData);

    }
}

