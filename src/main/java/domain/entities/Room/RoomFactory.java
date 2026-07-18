package domain.entities.Room;

/**
 * Interface for creating rooms.
 * The Use Cases will use this interface to load rooms by ID.
 */
public interface RoomFactory {
    Room createRoom(String roomName);
}