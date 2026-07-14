package domain.entities.Room;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class CommonRoom implements Room{

    private List<Interactable> interactables;
    private List<Item> items;
    private Boolean status;

    private final String Id;
    private final String description;

    private Boolean isLocked;


    public CommonRoom(String Id, String description, boolean isLocked) {
        this.Id = Id;
        this.description = description;
        this.isLocked = isLocked;
        this.interactables = new ArrayList<>();
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
        // Return a copy to protect the internal state from external modification
        return new ArrayList<>(interactables);
    }

    @Override
    public void addInteractable(Interactable interactable) {
        this.interactables.add(interactable);
    }

    @Override
    public Interactable getInteractableById(String id) {
        return interactables.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
        //TODO: waiting for the getter of interactable.
    }

}

