package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import domain.entities.Room.Room;

public interface StartUpDataAccessInterface {
    Room getRoomForQuickMode(String roomId);
}
