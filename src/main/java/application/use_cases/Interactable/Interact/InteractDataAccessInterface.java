package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Room.Room;
import domain.entities.User.User;

public interface InteractDataAccessInterface {
    User getUserById(String userId);
    Room getRoomById(String roomId);
    Interactable getInteractableById(String interactableId);
    Item getItemById(String itemId);
    Puzzle getPuzzleById(String puzzleId);
}