package application.use_cases.Hint.GetHint;

import domain.entities.Hint.Hint;
import domain.entities.User.User;

/**
 * Interactor for the Get Hint use case.
 *
 * <p>Retrieves a hint associated with an object, records the hint request
 * for the current user, and presents the appropriate hint message.</p>
 */
public class GetHintInteractor implements GetHintInputBoundary {
    private final GetHintOutputBoundary getHintPresenter;
    private final GetHintDataAccessInterface getHintDataAccessObject;
    private final GetHintUserDataAccessInterface getHintUserDataAccessObject;

    /**
     * Creates a {@code GetHintInteractor} with the specified presenter
     * and data access interfaces.
     *
     * @param getHintOutputBoundary the output boundary used to present
     *                              the hint result
     * @param getHintDataAccessInterface the data access interface used
     *                                   to retrieve hints
     * @param getHintUserDataAccessInterface the data access interface used
     *                                       to retrieve the current user
     */
    public GetHintInteractor(
            GetHintOutputBoundary getHintOutputBoundary,
            GetHintDataAccessInterface getHintDataAccessInterface,
            GetHintUserDataAccessInterface getHintUserDataAccessInterface) {
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

            final int calculatedIndex;

            if (currentUser.getHintsWatched() != null) {
                calculatedIndex = currentUser.getHintsWatched().getOrDefault(objectID, 0);
            }
            else {
                calculatedIndex = 0;
            }

            final String finalHintMessage =
                    hintObject.getHintMessageForRequestCount(calculatedIndex);

            final GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);
            getHintPresenter.prepareSuccessView(outputData);
        }
    }
}
