package domain.entities.room;

import domain.entities.hint.Hint;
import domain.entities.interactable.Interactable;
import domain.entities.item.Item;

import java.util.List;
import java.util.Map;

public class CommonRoomFactory implements RoomFactory {

    @Override
    public Room createRoom(String roomId, String description, String imagePath, List<Interactable> Interactable
    , List<Item> items, List<Hint> hint, Map<String, Position> positions) {

        return new CommonRoom(roomId,description,imagePath, Interactable, items, hint,positions);
    }
}