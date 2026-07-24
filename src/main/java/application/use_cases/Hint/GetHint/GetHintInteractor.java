package application.use_cases.Hint.GetHint;

import domain.entities.Hint.Hint;
import domain.entities.User.User;

/**
 * The interactor for the Get Hint use case, implementing the Get Hint input boundary.
 */
public class GetHintInteractor implements GetHintInputBoundary {
    private final GetHintOutputBoundary getHintPresenter;
    private final GetHintDataAccessInterface getHintDataAccessObject;

    public GetHintInteractor(GetHintOutputBoundary getHintOutputBoundary,
                             GetHintDataAccessInterface getHintDataAccessInterface) {
        this.getHintPresenter = getHintOutputBoundary;
        this.getHintDataAccessObject = getHintDataAccessInterface;
    }

    @Override
    public void execute(GetHintInputData getHintInputData) {
        String objectID = getHintInputData.getObjectID();

        if (!getHintDataAccessObject.existByObjectID(objectID)) {
            getHintPresenter.prepareFailView("No hints available");
            return;
        }

        User user = getHintInputData.getCurrentUser();
        Hint hintObject = getHintDataAccessObject.getHintForObjectID(objectID);

        user.saveHint(objectID, hintObject.getHintMessageCount()); // saves the hint request in-memory.

        int calculatedIndex = user.getHintsWatched().get(objectID);
        String finalHintMessage = hintObject.getHintMessageForRequestCount(calculatedIndex);

        GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);
        getHintPresenter.prepareSuccessView(outputData);
    }
}
