package domain.entities.Room;

import domain.entities.Interactable.Interactable;

import java.util.List;

/**
 * Core domain interface representing a Room entity.
 */
public interface Room {
    String getId();
    String getDescription();

    // Navigation & State
    boolean isLocked();
    void unlock();

    // Content management
    List<Interactable> getInteractables();
    void addInteractable(Interactable interactable);
    void  removeInteractable(Interactable interactable);
    Interactable getInteractableById(String id);
}