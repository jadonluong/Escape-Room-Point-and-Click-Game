package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.List;

/**
 * Core domain interface representing a Room entity.
 */
public interface Room {
    String getId();
    String getDescription();

    // Content management
    List<Interactable> getInteractables();
    void addInteractable(Interactable interactable);
    void  removeInteractable(String interactableId);
    Interactable getInteractableById(String id);

    List<Item> getItems();
    void addItem(Item item);
    void removeItem(Item item);

    List<Hint> getHints();
    void setHint(Hint hint);

    String getImagePath();
}