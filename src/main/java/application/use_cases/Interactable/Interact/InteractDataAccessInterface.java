package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Room.Room;
import domain.entities.User.User;

public interface InteractDataAccessInterface {
    User getUserById(String id);
    void saveUser(User user);

    Room getRoomById(String id);
    void saveRoom(Room room);

    Interactable getInteractableById(String id);
    void saveInteractable(Interactable interactable);

    Item getItemById(String id);
    void saveItem(Item item);
}