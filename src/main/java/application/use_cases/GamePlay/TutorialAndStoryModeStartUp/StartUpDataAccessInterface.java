package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

import domain.entities.Room.Room;

public interface StartUpDataAccessInterface {
    Room findStartingRoomForTut();
    Room findStartingRoomForStory();
}
