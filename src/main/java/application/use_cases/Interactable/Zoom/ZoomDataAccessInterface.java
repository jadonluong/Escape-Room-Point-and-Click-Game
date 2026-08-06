package application.use_cases.Interactable.Zoom;

import domain.entities.Interactable.Interactable;
import domain.entities.Puzzle.Puzzle;

public interface ZoomDataAccessInterface {
    Interactable getInteractableById(String interactableId);
    Puzzle getPuzzleById(String puzzleId);
}
