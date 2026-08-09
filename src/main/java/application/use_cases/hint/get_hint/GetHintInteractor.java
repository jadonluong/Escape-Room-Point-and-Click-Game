package application.use_cases.hint.get_hint;

import java.util.Map;

import domain.entities.hint.Hint;
import domain.entities.user.User;

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

    /**
     * Retrieves and presents the hint associated with the specified object.
     *
     * <p>If no hint is available for the object, a failure view is prepared.
     * Otherwise, the hint request is recorded and the appropriate hint message
     * is presented based on the user's previous hint requests.</p>
     *
     * @param getHintInputData the input data containing the ID of the object
     *                         for which a hint is requested
     */
    @Override
    public void execute(GetHintInputData getHintInputData) {
        final String objectID = getHintInputData.getObjectID();

        if (!getHintDataAccessObject.existByObjectID(objectID)) {
            getHintPresenter.prepareFailView("No hints available");
        }
        else {
            final User currentUser = getHintUserDataAccessObject.getCurrentUser();

            final Hint hintObject = getHintDataAccessObject.getHintForObjectID(objectID);

            // saves the hint request in-memory.
            currentUser.saveHint(objectID, hintObject.getHintMessageCount());

            final Map<String, Integer> hintsWatched = currentUser.getHintsWatched();
            final int calculatedIndex;

            if (hintsWatched != null) {
                calculatedIndex = hintsWatched.getOrDefault(objectID, 0);
            }
            else {
                calculatedIndex = 0;
            }

            final String finalHintMessage = hintObject.getHintMessageForRequestCount(calculatedIndex);

            final GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);

            getHintPresenter.prepareSuccessView(outputData);
        }
    }
}
