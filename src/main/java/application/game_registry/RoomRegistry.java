package application.game_registry;

import domain.entities.Room.Room;

/**
 * The registry for getting Room objects with their roomIDs.
 */
public interface RoomRegistry {

    /**
     * Returns the Room object with the given ID.
     * @param roomID the room ID
     * @return the Room object with roomID
     */
    Room getRoomById(String roomID);
}
