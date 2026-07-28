package interface_adapter.User.SaveProgress;

import application.use_cases.User.SaveProgress.SaveProgressInputBoundary;
import application.use_cases.User.SaveProgress.SaveProgressInputData;
import domain.entities.User.User;


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
     * @param user the User object of the user clicking save or save&logout
     */
    public void execute(User user) {
        SaveProgressInputData inputData = new SaveProgressInputData(user);
        this.saveProgressInteractor.execute(inputData);
    }
}
