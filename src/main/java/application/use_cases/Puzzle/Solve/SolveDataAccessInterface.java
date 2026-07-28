package application.use_cases.Puzzle.Solve;

import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public interface SolveDataAccessInterface {
    Puzzle getPuzzleById(String puzzleId);
    Item getItemById(String itemId);
}
