package application.use_cases.Hint.GetHint;

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
        }

        int requestCount = getHintInputData.getRequestCount();
        String message = getHintDataAccessObject.getHintMessageForRequestCount(objectID, requestCount);
        GetHintOutputData outputData = new GetHintOutputData(message);
        getHintPresenter.prepareSuccessView(outputData);
    }
}
