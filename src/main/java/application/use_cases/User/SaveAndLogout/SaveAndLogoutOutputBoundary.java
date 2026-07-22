package application.use_cases.User.SaveAndLogout;

/**
 * The output boundary for the Save and Logout use case.
 */
public interface SaveAndLogoutOutputBoundary {

    /**
     * Prepares the success view for the Save and Logout use case when the user progress is saved.
     * @param outputData the output data of the Save and Logout use case
     */
    void prepareSavedSuccessView(SaveAndLogoutOutputData outputData);

    /**
     * Prepares the fail view for the Save and Logout use case.
     * @param errorMessage the error message to be displayed
     */
    void prepareFailView(String errorMessage);
}
