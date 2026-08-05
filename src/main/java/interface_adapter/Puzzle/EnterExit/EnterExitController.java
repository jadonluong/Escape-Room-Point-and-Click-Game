package interface_adapter.Puzzle.EnterExit;

import application.use_cases.Puzzle.EnterExit.EnterExitInputBoundary;
import application.use_cases.Puzzle.EnterExit.EnterExitInputData;

public class EnterExitController {
    private final EnterExitInputBoundary inputBoundary;

    public EnterExitController(EnterExitInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void enter(String puzzleId, String interactableId) {
        final EnterExitInputData inputData = new EnterExitInputData(puzzleId, interactableId);
        inputBoundary.enter(inputData);
    }

    public void exit() {
        inputBoundary.exit();
    }
}
