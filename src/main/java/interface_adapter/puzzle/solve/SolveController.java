package interface_adapter.puzzle.solve;

import application.use_cases.puzzle.solve.SolveInputBoundary;
import application.use_cases.puzzle.solve.SolveInputData;

public class SolveController {
    private final SolveInputBoundary solveInputBoundary;

    public SolveController(SolveInputBoundary solveInputBoundary) {
        this.solveInputBoundary = solveInputBoundary;
    }

    public void solve(String puzzleId, String playerAnswer, String interactableId) {
        final SolveInputData inputData = new SolveInputData(puzzleId, playerAnswer, interactableId);
        solveInputBoundary.solve(inputData);
    }
}
