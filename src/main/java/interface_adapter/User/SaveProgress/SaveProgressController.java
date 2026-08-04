package interface_adapter.User.SaveProgress;

import application.use_cases.User.SaveProgress.SaveProgressInputBoundary;
import application.use_cases.User.SaveProgress.SaveProgressInputData;

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
        SaveProgressInputData inputData = new SaveProgressInputData(username);
        this.saveProgressInteractor.execute(inputData);
    }
}
