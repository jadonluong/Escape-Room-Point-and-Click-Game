package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.List;

/**
 * Interface for creating rooms.
 * The Use Cases will use this interface to load rooms by ID.
 */
public interface RoomFactory {
    Room createRoom(String roomId, String description, String path, List<Interactable> Interactable
            , List<Item> items, List<Hint> hint);
}