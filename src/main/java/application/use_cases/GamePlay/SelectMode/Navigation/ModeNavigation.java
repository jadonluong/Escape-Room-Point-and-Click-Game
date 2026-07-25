package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsInputBoundary;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Room.Room;

public interface ModeNavigation {
    default void navigate(SelectModeOutputBoundary presenter) {
    }

    default void navigate(BrowseRoomsOutputBoundary presenter) {
    }
}
