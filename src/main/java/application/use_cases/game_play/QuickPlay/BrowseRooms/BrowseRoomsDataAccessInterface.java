package application.use_cases.game_play.QuickPlay.BrowseRooms;

import java.util.List;

import domain.entities.Room.Room;

public interface BrowseRoomsDataAccessInterface {

    /**
     * Retrieves the list of rooms available for quick mode.
     *
     * @return a list of rooms that can be used for quick mode
     */
    List<Room> getRoomsForQuickMode();
}
