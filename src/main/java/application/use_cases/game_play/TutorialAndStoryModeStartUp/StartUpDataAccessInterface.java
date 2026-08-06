package application.use_cases.game_play.TutorialAndStoryModeStartUp;

import domain.entities.Room.Room;

public interface StartUpDataAccessInterface {
    Room findStartingRoomForTut();
    Room findStartingRoomForStory();
}
