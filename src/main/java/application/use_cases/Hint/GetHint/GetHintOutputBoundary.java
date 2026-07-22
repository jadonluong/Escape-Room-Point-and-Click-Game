package application.use_cases.Hint.GetHint;

/**
 * The output boundary of the Get Hint use case.
 */
public interface GetHintOutputBoundary {

    /**
     * Prepares the success view of the Get Hint use case.
     * @param getHintOutputData the output data of the Get Hint use case
     */
    void prepareSuccessView(GetHintOutputData getHintOutputData);

    /**
     * Prepares the fail view of the Get Hint use case.
     * @param errorMessage the error message to be displayed
     */
    void prepareFailView(String errorMessage);
}
