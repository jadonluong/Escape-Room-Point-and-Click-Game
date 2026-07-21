package interface_adapter.User.Logout;

import application.use_cases.User.Logout.LogoutInputBoundary;
import application.use_cases.User.Logout.LogoutInputData;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutInputBoundary;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutInputData;
import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void executeLogoutWithSave(String username,
                                      ArrayList<Room> roomsUnlocked,
                                      ArrayList<Item> itemInventory,
                                      HashMap<String, Integer> hintsWatched,
                                      boolean isRegistered) {

        SaveAndLogoutInputData inputData = new SaveAndLogoutInputData(username,
                roomsUnlocked, itemInventory, hintsWatched, isRegistered);
        saveAndLogoutInteractor.execute(inputData);
    }
}
