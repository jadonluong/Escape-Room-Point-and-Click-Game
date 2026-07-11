package domain.entities.Room;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.List;

public interface RoomFactory {
    Room create(String id, List<Interactable> interactables, List<Item> items);
}
