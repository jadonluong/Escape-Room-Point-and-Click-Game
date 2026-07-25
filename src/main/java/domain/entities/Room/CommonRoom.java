package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonRoom implements Room{

    private List<Interactable> interactables;
    private List<Item> items;

    private List<Hint> hints;

    private final String Id;
    private final String description;
    private final String imagePath;




    public CommonRoom(String Id, String description, String imagePath, List<Interactable> Interactable, List<Item> item
    , List<Hint> hint) {
        this.Id = Id;
        this.description = description;
        this.imagePath = imagePath;
        this.interactables = Interactable;
        this.items = item;
        this.hints = hint;
    }


    @Override
    public String getId() { return Id; }

    @Override
    public String getDescription() { return description; }

    @Override
    public List<Interactable> getInteractables() {
        return interactables;
    }
    @Override
    public void addInteractable(Interactable interactable) {
        this.interactables.add(interactable);
    }

    @Override
    public void removeInteractable(Interactable interactable) {
        this.interactables.remove(interactable);
    }

    @Override
    public Interactable getInteractableById(String id) {
        for  (Interactable interactable : interactables) {
            if(interactable.getId().equals(id)) {
                return interactable;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
    public List<Hint> getHints() {
        return new ArrayList<>(hints);
    }

}

