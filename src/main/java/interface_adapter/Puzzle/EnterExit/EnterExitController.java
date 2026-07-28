package interface_adapter.Puzzle.EnterExit;

import application.use_cases.Puzzle.EnterExit.EnterExitInputBoundary;
import application.use_cases.Puzzle.EnterExit.EnterExitInputData;
import domain.entities.User.User;

public class EnterExitController {
    private final EnterExitInputBoundary inputBoundary;

    public EnterExitController(EnterExitInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void enter(User user, String puzzleId) {
        final EnterExitInputData inputData = new EnterExitInputData(user, puzzleId);
        inputBoundary.enter(inputData);
    }

    public void exit() {
        inputBoundary.exit();
    }
}
