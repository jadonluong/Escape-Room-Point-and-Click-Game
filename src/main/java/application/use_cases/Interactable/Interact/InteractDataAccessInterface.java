package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Room.Room;

public interface InteractDataAccessInterface {
    Room getRoomById(String roomId);
    Interactable getInteractableById(String interactableId);
    Item getItemById(String itemId);
    Puzzle getPuzzleById(String puzzleId);
}