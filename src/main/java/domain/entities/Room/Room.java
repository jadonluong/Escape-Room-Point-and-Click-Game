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

    // Content management
    List<Interactable> getInteractables();
    void addInteractable(Interactable interactable);
    void  removeInteractable(Interactable interactableId);
    Interactable getInteractableById(String id);
}