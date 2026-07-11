package domain.entities.Room;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.List;
import java.util.UUID;

public class CommonRoom {
    private UUID id;
    private List<Interactable> interactables;
    private List<Item> items;
    private Boolean status;
}
