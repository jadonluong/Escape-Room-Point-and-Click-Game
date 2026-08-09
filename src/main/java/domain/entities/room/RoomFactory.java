package domain.entities.room;

import domain.entities.hint.Hint;
import domain.entities.interactable.Interactable;
import domain.entities.item.Item;

import java.util.List;
import java.util.Map;

/**
 * Interface for creating rooms.
 * The Use Cases will use this interface to load rooms by ID.
 */
public interface RoomFactory {
    Room createRoom(String roomId, String description, String imagePath, List<Interactable> Interactable
            , List<Item> items, List<Hint> hint, Map<String, Position> positions);
}