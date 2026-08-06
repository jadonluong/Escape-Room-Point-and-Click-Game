package application.use_cases.Hint.GetHint;

import domain.entities.Hint.Hint;
import domain.entities.User.User;

/**
 * The interactor for the Get Hint use case, implementing the Get Hint input boundary.
 */
public class GetHintInteractor implements GetHintInputBoundary {
    private final GetHintOutputBoundary getHintPresenter;
    private final GetHintDataAccessInterface getHintDataAccessObject;
    private final GetHintUserDataAccessInterface getHintUserDataAccessObject;

    public GetHintInteractor(GetHintOutputBoundary getHintOutputBoundary,
                             GetHintDataAccessInterface getHintDataAccessInterface,
                             GetHintUserDataAccessInterface getHintUserDataAccessInterface
                             ) {
        this.getHintPresenter = getHintOutputBoundary;
        this.getHintDataAccessObject = getHintDataAccessInterface;
        this.getHintUserDataAccessObject = getHintUserDataAccessInterface;
    }

    @Override
    public void execute(GetHintInputData getHintInputData) {
        String objectID = getHintInputData.getObjectID();

        if (!getHintDataAccessObject.existByObjectID(objectID)) {
            getHintPresenter.prepareFailView("No hints available");
            return;
        }

        User currentUser = getHintUserDataAccessObject.getCurrentUser();

        Hint hintObject = getHintDataAccessObject.getHintForObjectID(objectID);

        currentUser.saveHint(objectID, hintObject.getHintMessageCount()); // saves the hint request in-memory.

        int calculatedIndex = (currentUser.getHintsWatched() != null) ? currentUser.getHintsWatched().getOrDefault(objectID, 0) : 0;
        String finalHintMessage = hintObject.getHintMessageForRequestCount(calculatedIndex);

        GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);
        getHintPresenter.prepareSuccessView(outputData);
    }
}
