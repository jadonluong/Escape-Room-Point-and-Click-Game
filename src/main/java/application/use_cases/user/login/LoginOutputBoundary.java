package application.use_cases.user.login;

/**
 * The output boundary for the Login Use Case.
 */
public interface LoginOutputBoundary {

    /**
     * Prepares the failed view for the Login Use Case.
     * @param errorMessage the error message to be displayed
     */
    void prepareFailView(String errorMessage);

    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data of the login use case.
     */
    void prepareSuccessView(LoginOutputData outputData);
}
