package application.use_cases.Puzzle.Solve;

import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public interface SolveDataAccessInterface {
    User getUserById(String userId);
    void saveUser(User user);

    Puzzle getPuzzleById(String puzzleId);
    void savePuzzle(Puzzle puzzle);

    Item getItemById(String itemId);
}
