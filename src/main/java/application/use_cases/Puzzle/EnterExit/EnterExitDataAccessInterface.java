package application.use_cases.Puzzle.EnterExit;

import domain.entities.Puzzle.Puzzle;

public interface EnterExitDataAccessInterface {
    Puzzle getPuzzleById(String puzzleId);
}
