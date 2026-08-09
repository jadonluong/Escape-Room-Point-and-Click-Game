package application.use_cases.user.logout;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LogoutInputBoundary {

    /**
     * Executes the Logout Use Case.
     * @param logoutInputData the logout input data.
     */
    void execute(LogoutInputData logoutInputData);
}
