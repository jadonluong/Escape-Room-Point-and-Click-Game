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
        final String objectID = getHintInputData.getObjectID();

        if (!getHintDataAccessObject.existByObjectID(objectID)) {
            getHintPresenter.prepareFailView("No hints available");
            return;
        }

        final User currentUser = getHintUserDataAccessObject.getCurrentUser();

        final Hint hintObject = getHintDataAccessObject.getHintForObjectID(objectID);

        // saves the hint request in-memory.
        currentUser.saveHint(objectID, hintObject.getHintMessageCount());

        final int calculatedIndex = (currentUser.getHintsWatched() != null) ? currentUser
                                                                        .getHintsWatched()
                                                                        .getOrDefault(objectID, 0) : 0;
        final String finalHintMessage = hintObject.getHintMessageForRequestCount(calculatedIndex);

        final GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);
        getHintPresenter.prepareSuccessView(outputData);
    }
}
