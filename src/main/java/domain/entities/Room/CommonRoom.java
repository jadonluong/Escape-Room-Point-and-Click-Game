package domain.entities.Room;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonRoom implements Room{

    private Map<String, Interactable> interactables;
    private List<Item> items;
    private Boolean status;

    private final String Id;
    private final String description;
    private final String imagePath;

    private Boolean isLocked;


    public CommonRoom(String Id, String description, String imagePath, boolean isLocked) {
        this.Id = Id;
        this.description = description;
        this.imagePath = imagePath;
        this.isLocked = isLocked;
        this.interactables = new HashMap<>();

    }


    @Override
    public String getId() { return Id; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isLocked() { return isLocked; }

    @Override
    public void unlock() { this.isLocked = false; }

    @Override
    public List<Interactable> getInteractables() {
        // FIX: Extract and return a copy of the Map's values as a List
        return new ArrayList<>(interactables.values());
    }
    @Override
    public void addInteractable(Interactable interactable) {
        this.interactables.put(interactable.getId(), interactable);
    }

    @Override
    public void removeInteractable(String id) {
        this.interactables.remove(id);
    }

    @Override
    public Interactable getInteractableById(String id) {
        return this.interactables.get(id);
    }

}

