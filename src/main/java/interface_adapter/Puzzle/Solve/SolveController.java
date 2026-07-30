package interface_adapter.Puzzle.Solve;

import application.use_cases.Puzzle.Solve.SolveInputBoundary;
import application.use_cases.Puzzle.Solve.SolveInputData;

public class SolveController {
    private final SolveInputBoundary solveInputBoundary;

    public SolveController(SolveInputBoundary solveInputBoundary) {
        this.solveInputBoundary = solveInputBoundary;
    }

    public void solve(String puzzleId, String playerAnswer) {
        final SolveInputData inputData = new SolveInputData(puzzleId, playerAnswer);
        solveInputBoundary.solve(inputData);
    }
}
