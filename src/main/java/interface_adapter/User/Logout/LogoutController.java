package interface_adapter.User.Logout;

import application.use_cases.User.Logout.LogoutInputBoundary;
import application.use_cases.User.Logout.LogoutInputData;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutInputBoundary;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutInputData;
import domain.entities.User.User;

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
        LogoutInputData inputData = new LogoutInputData(username);
        logoutInteractor.execute(inputData);
    }

    /**
     * Executes the save and logout use case.
     * @param user the current user
     */
    public void executeLogoutWithSave(User user) {

        SaveAndLogoutInputData inputData = new SaveAndLogoutInputData(user);
        saveAndLogoutInteractor.execute(inputData);
    }
}
