package application.use_cases.Hint.GetHint;

import domain.entities.User.User;

import java.util.List;

/**
 * The interactor for the Get Hint use case, implementing the Get Hint input boundary.
 */
public class GetHintInteractor implements GetHintInputBoundary {
    private GetHintOutputBoundary getHintPresenter;
    private GetHintDataAccessInterface getHintDataAccessObject;

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

        int maxHintsAvailable = getHintDataAccessObject.getMaxHintsAvailable(objectID);
        user.saveHint(objectID, maxHintsAvailable); // saves the hint request in-memory.

        int calculatedIndex = user.getHintsWatched().get(objectID);

        List<String> allHints = getHintDataAccessObject.getAllHintsForObject(objectID);
        String finalHintMessage = allHints.get(calculatedIndex);

        GetHintOutputData outputData = new GetHintOutputData(finalHintMessage);
        getHintPresenter.prepareSuccessView(outputData);
    }
}
