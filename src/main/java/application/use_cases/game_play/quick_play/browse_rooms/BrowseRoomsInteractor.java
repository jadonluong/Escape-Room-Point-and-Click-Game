package application.use_cases.game_play.quick_play.browse_rooms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.room.Room;

/**
 * Interactor responsible for retrieving and presenting rooms available
 * for Quick Play.
 */
public class BrowseRoomsInteractor implements BrowseRoomsInputBoundary {
    private final BrowseRoomsDataAccessInterface dataAccess;
    private final BrowseRoomsOutputBoundary presenter;

    /**
     * Creates a {@code BrowseRoomsInteractor} with the specified data access
     * interface and presenter.
     *
     * @param dataAccess the data access interface used to retrieve Quick Play rooms
     * @param presenter the output boundary used to present the retrieved rooms
     */
    public BrowseRoomsInteractor(BrowseRoomsDataAccessInterface dataAccess,
                                 BrowseRoomsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Retrieves the rooms available for Quick Play and presents them.
     *
     * <p>If no rooms are available, a failure view is prepared. Otherwise,
     * the room information is converted into output data and passed to
     * the presenter.</p>
     */
    @Override
    public void execute() {
        // Fetch domain entities via abstract interface
        final List<Room> rooms = dataAccess.getRoomsForQuickMode();

        if (rooms.isEmpty()) {
            presenter.prepareFailView("No rooms found");
        }
        else {
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
}
