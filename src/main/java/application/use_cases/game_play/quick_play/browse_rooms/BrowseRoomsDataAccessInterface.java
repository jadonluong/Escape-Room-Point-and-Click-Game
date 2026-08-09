package application.use_cases.game_play.quick_play.browse_rooms;

import java.util.List;

import domain.entities.room.Room;

public interface BrowseRoomsDataAccessInterface {

    /**
     * Retrieves the list of rooms available for quick mode.
     *
     * @return a list of rooms that can be used for quick mode
     */
    List<Room> getRoomsForQuickMode();
}
