package application.use_cases.game_play.QuickPlay.BrowseRooms;

import java.util.Map;

/**
 * Output data containing information about the rooms available for Quick Play.
 */
public class BrowseRoomsOutputData {
    private final Map<String, RoomInfo> info;

    /**
     * Creates output data containing room information.
     *
     * @param info a map containing room IDs and their corresponding room information
     */
    public BrowseRoomsOutputData(Map<String, RoomInfo> info) {
        this.info = info;
    }

    /**
     * Returns the information about the available rooms.
     *
     * @return a map containing room IDs and their corresponding room information
     */
    public Map<String, RoomInfo> getInfo() {
        return info;
    }
}
