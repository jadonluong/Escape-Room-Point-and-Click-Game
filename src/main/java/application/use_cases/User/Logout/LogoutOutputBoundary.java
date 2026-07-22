package application.use_cases.User.Logout;

/**
 * The output boundary for the Logout Use Case.
 */
public interface LogoutOutputBoundary {

    /**
     * Prepares the success view for the Logout Use Case when the user progress is unsaved.
     * @param logoutOutputData the output data of the logout use case
     */
    void prepareUnsavedSuccessView(LogoutOutputData logoutOutputData);
}
