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
        this.interactables = Interactable;
        this.items = item;
        this.hints = hint;
        this.imagePath = imagePath;
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
    public void removeInteractable(String interactable) {
        for (Interactable interactable1 : this.interactables) {
            if (interactable1.getId().equals(interactable)) {
                this.interactables.remove(interactable1);
            }
        }
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

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public List<Hint> getHints() {
        return hints;
    }

    @Override
    public void setHint(Hint hint) {
        hints.add(hint);
    }

    public String getImagePath() { return imagePath; }

}

