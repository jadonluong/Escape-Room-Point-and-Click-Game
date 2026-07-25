package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

import domain.entities.Room.Room;

public interface StartUpDataAccessInterface {
    String findStartingRoomForTut();
    String findStartingRoomForStory();
    Room findRoom(String roomId);
}
