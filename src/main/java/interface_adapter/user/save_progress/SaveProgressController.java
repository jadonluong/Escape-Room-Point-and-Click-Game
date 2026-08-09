package interface_adapter.user.save_progress;

import application.use_cases.user.save_progress.SaveProgressInputBoundary;
import application.use_cases.user.save_progress.SaveProgressInputData;

/**
 * The controller for the Save Progress Use Case.
 */
public class SaveProgressController {
    private final SaveProgressInputBoundary saveProgressInteractor;

    public SaveProgressController(SaveProgressInputBoundary saveProgressInputBoundary) {
        this.saveProgressInteractor = saveProgressInputBoundary;
    }

    /**
     * Executes the Save Progress Use case.
     * @param username the username of the user clicking save or save&logout
     */
    public void execute(String username) {
        final SaveProgressInputData inputData = new SaveProgressInputData(username);
        this.saveProgressInteractor.execute(inputData);
    }
}
