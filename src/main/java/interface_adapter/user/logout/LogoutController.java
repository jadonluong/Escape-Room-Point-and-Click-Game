package interface_adapter.user.logout;

import application.use_cases.user.logout.LogoutInputBoundary;
import application.use_cases.user.logout.LogoutInputData;
import application.use_cases.user.save_and_logout.SaveAndLogoutInputBoundary;
import application.use_cases.user.save_and_logout.SaveAndLogoutInputData;

/**
 * The controller for the Logout Use Case.
 */
public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;
    private final SaveAndLogoutInputBoundary saveAndLogoutInteractor;

    public LogoutController(LogoutInputBoundary logoutInteractor,
                            SaveAndLogoutInputBoundary saveAndLogoutInteractor) {
        this.logoutInteractor = logoutInteractor;
        this.saveAndLogoutInteractor = saveAndLogoutInteractor;
    }

    /**
     * Executes the logout use case. The user progress is not automatically saved by clicking logout.
     * @param username the username of the user logging out
     */
    public void executeLogoutWithoutSave(String username) {
        final LogoutInputData inputData = new LogoutInputData(username);
        logoutInteractor.execute(inputData);
    }

    /**
     * Executes the save and logout use case.
     * @param username the username of the current user
     */
    public void executeLogoutWithSave(String username) {

        final SaveAndLogoutInputData inputData = new SaveAndLogoutInputData(username);
        saveAndLogoutInteractor.execute(inputData);
    }
}
