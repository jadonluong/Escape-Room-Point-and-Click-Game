package application.use_cases.Interactable.Zoom;

import domain.entities.Interactable.Interactable;
import domain.entities.User.User;

public interface ZoomDataAccessInterface {
    User getUserById(String userId);
    Interactable getInteractableById(String interactableId);
}
