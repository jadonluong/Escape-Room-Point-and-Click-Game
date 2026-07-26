package domain.entities.Room;

import domain.entities.Hint.Hint;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;

import java.util.List;
import java.util.Map;

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
    void setPosition(String Id, String x, String y);
    Position getPosition(String Id);
}