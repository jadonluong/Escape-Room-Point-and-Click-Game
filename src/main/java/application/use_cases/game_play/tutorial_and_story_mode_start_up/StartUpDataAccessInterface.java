package application.use_cases.game_play.tutorial_and_story_mode_start_up;

import domain.entities.room.Room;

/**
 * Data access interface for retrieving the starting rooms for tutorial
 * and story modes.
 */
public interface StartUpDataAccessInterface {

    /**
     * Retrieves the starting room for tutorial mode.
     *
     * @return the starting tutorial room
     */
    Room findStartingRoomForTut();

    /**
     * Retrieves the starting room for story mode.
     *
     * @return the starting story room
     */
    Room findStartingRoomForStory();
}
