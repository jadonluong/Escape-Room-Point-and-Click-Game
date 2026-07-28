package interface_adapter.Puzzle.Solve;

import application.use_cases.Puzzle.Solve.SolveInputBoundary;
import application.use_cases.Puzzle.Solve.SolveInputData;
import domain.entities.User.User;

public class SolveController {
    private final SolveInputBoundary solveInputBoundary;

    public SolveController(SolveInputBoundary solveInputBoundary) {
        this.solveInputBoundary = solveInputBoundary;
    }

    public void solve(User user, String puzzleId, String playerAnswer) {
        final SolveInputData inputData = new SolveInputData(user, puzzleId, playerAnswer);
        solveInputBoundary.solve(inputData);
    }
}
