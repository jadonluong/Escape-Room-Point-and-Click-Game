package domain.entities.user;

import java.util.List;

public interface UserInteractables {

    /**
     * Saves the interactable with the given id.
     * @param interactableId the id of teh interactable
     */
    void saveInteractable(String interactableId);

    /**
     * Returns the map of interactables the user has interacted with.
     * @return the map of interactables the user has interacted with
     */
    List<String> getStoryModeInteractables();

}
