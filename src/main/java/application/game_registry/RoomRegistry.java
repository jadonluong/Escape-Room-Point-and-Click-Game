package application.game_registry;

import domain.entities.Room.Room;

/**
 * The registry for rooms.
 */
public interface RoomRegistry {

    /**
     * Returns the Room object with the given ID.
     * @param roomID the room ID
     * @return the Room object with roomID
     */
    Room getRoomByID(String roomID);
}
