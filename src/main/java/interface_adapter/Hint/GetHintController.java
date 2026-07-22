package interface_adapter.Hint;

import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import domain.entities.User.User;

/**
 * The controller for the Get Hint Use Case.
 */
public class GetHintController {
    private final GetHintInputBoundary getHintInteractor;

    public GetHintController(GetHintInputBoundary getHintInteractor) {
        this.getHintInteractor = getHintInteractor;
    }

    /**
     * Executes the Get Hint Use Case.
     * @param objectID the ID of the object the user clicked on
     * @param currentUser the user requesting the hint
     */
    public void execute(String objectID, User currentUser) {
        GetHintInputData inputData = new GetHintInputData(objectID, currentUser);
        getHintInteractor.execute(inputData);
    }
}
