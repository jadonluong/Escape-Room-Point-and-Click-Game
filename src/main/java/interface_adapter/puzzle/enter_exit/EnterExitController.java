package interface_adapter.puzzle.enter_exit;

import application.use_cases.puzzle.enter_exit.EnterExitInputBoundary;
import application.use_cases.puzzle.enter_exit.EnterExitInputData;

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
