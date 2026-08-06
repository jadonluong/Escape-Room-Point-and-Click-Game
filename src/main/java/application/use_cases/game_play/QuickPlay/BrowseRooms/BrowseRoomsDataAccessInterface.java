package application.use_cases.game_play.QuickPlay.BrowseRooms;

import domain.entities.Room.Room;

import java.util.List;

public interface BrowseRoomsDataAccessInterface {
    List<Room> getRoomsForQuickMode();
}
