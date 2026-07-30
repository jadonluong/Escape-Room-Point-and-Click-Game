package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

import domain.entities.Room.Room;

import java.util.List;

public interface BrowseRoomsDataAccessInterface {
    List<Room> getRoomsForQuickMode();
}
