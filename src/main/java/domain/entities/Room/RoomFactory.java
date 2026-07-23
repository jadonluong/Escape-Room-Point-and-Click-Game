package domain.entities.Room;

import java.util.List;

/**
 * Interface for creating rooms.
 * The Use Cases will use this interface to load rooms by ID.
 */
public interface RoomFactory {
    Room createRoom(String roomId, String description, String path, List<String> Interactable);
}