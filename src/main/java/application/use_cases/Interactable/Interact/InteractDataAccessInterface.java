package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Room.Room;
import domain.entities.User.User;

public interface InteractDataAccessInterface {
    User getUserById(String userId);
    void saveUser(User user);

    Room getRoomById(String roomId);
    void saveRoom(Room room);

    Interactable getInteractableById(String interactableId);
    void saveInteractable(Interactable interactable);

    Item getItemById(String itemId);
    void saveItem(Item item);

    Puzzle getPuzzleById(String puzzleId);
    void savePuzzle(Puzzle puzzle);
}