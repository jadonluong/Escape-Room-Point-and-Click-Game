package application.use_cases.puzzle.solve;

import domain.entities.item.Item;
import domain.entities.puzzle.Puzzle;

public interface SolveDataAccessInterface {
    Puzzle getPuzzleById(String puzzleId);
    Item getItemById(String itemId);
}
