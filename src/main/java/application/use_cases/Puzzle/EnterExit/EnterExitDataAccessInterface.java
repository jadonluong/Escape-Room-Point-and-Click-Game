package application.use_cases.Puzzle.EnterExit;

import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public interface EnterExitDataAccessInterface {
    User getCurrentUser();
    Puzzle getPuzzleById(String puzzleId);
}
