package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;

import java.util.List;

public class CommonRoomFactory implements RoomFactory {

    @Override
    public Room createRoom(String roomId, String description,String imagePath, List<Interactable> Interactable
    , List<Item> items, List<Hint> hint) {

        return new CommonRoom(roomId,description,imagePath, Interactable, items, hint);
    }
}