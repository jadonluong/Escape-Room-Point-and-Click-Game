package application.use_cases.GamePlay.SelectMode;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputData;
import domain.entities.Room.Room;

import java.util.List;

public interface SelectModeOutputBoundary {
    void prepareGameStartView(String RoomId);
    void prepareFailView(String message);
}
