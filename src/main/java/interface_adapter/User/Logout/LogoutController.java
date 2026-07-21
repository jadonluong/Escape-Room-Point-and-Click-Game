package interface_adapter.User.Logout;

import application.use_cases.User.Logout.LogoutInputBoundary;
import application.use_cases.User.Logout.LogoutInputData;

/**
 * The controller for the Logout Use Case.
 */
public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;

    public LogoutController(LogoutInputBoundary logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    /**
     * Executes the logout use case. The user progress is not automatically saved by clicking logout.
     * @param username the username of the user logging out
     */
    public void executeLogoutWithoutSave(String username) {
        LogoutInputData inputData = new LogoutInputData(username);
        logoutInteractor.execute(inputData);
    }
}
