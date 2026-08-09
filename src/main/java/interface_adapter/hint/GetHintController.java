package interface_adapter.hint;

import application.use_cases.hint.get_hint.GetHintInputBoundary;
import application.use_cases.hint.get_hint.GetHintInputData;

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
     */
    public void execute(String objectID) {
        final GetHintInputData inputData = new GetHintInputData(objectID);
        getHintInteractor.execute(inputData);
    }
}
