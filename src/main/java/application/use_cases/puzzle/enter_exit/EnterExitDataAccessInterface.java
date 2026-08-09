package application.use_cases.puzzle.enter_exit;

import domain.entities.puzzle.Puzzle;

public interface EnterExitDataAccessInterface {
    Puzzle getPuzzleById(String puzzleId);
}
