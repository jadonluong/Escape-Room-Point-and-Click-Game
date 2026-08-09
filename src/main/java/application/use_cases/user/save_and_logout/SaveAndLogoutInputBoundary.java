package application.use_cases.user.save_and_logout;

/**
 * The input boundary for the Save and Logout use case.
 */
public interface SaveAndLogoutInputBoundary {

    /**
     * Executes the Save and Logout use case.
     * @param saveAndLogoutInputData the input data for the Save and Logout use case
     */
    void execute(SaveAndLogoutInputData saveAndLogoutInputData);
}
