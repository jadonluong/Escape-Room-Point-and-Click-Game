package domain.entities.User;

import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * The interface with methods that tracks and records the user's progress.
 */
public interface UserProgress {

    /**
     * Returns the rooms the user has unlocked.
     */
    ArrayList<Room> getRoomsUnlocked();

    /**
     * Saves the newly unclocked room to the user's unlocked room inventory.
     * @param room the newly unlocked room.
     */
    void unlockRoom(Room room);

    /**
     * Saves the newly unclocked room to the user's unlocked room inventory.
     * @param roomID the room to be set to current room.
     */
    void saveCurrentRoomID(String roomID);

    /**
     * Returns the ID of the current room the user is in.
     * @return the ID of the current room the user is in.
     */
    String getCurrentRoomID();

    /**
     * Returns the ID of the room the user last entered in story mode.
     * @return the ID of the room the user last entered in story mode.
     */
    String getStoryModeCurrentRoomID();

    /**
     * Switches the user to another room. This is called after the user unlocks a room
     * @param room the room the user is entering
     */
    void switchRoom(Room room);
}
