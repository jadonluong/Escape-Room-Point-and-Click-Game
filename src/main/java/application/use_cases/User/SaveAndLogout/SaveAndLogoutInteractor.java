package application.use_cases.User.SaveAndLogout;

import application.use_cases.User.Logout.LogoutUserDataAccessInterface;
import application.use_cases.User.SaveProgress.SaveProgressUserDataAccessInterface;
import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * The interactor for the Save and Logout use case.
 */
public class SaveAndLogoutInteractor implements SaveAndLogoutInputBoundary {
    private final SaveProgressUserDataAccessInterface saveProgressDataAccessObject;
    private final LogoutUserDataAccessInterface logoutDataAccessObject;
    private final SaveAndLogoutOutputBoundary saveAndLogoutPresenter;

    public SaveAndLogoutInteractor(SaveProgressUserDataAccessInterface saveProgressDataAccess,
                                   LogoutUserDataAccessInterface logoutDataAccess,
                                   SaveAndLogoutOutputBoundary saveAndLogoutPresenter) {
        this.saveProgressDataAccessObject = saveProgressDataAccess;
        this.logoutDataAccessObject = logoutDataAccess;
        this.saveAndLogoutPresenter = saveAndLogoutPresenter;
    }

    @Override
    public void execute(SaveAndLogoutInputData inputData) {
        // 1. Core Rule Validation
        if (!inputData.getRegisteredStatus()) {
            saveAndLogoutPresenter.prepareFailView("User is in Guest Mode, progress cannot be saved.");
            return;
        }

        // 2. Perform Save Silently (Bypasses regular save presenter)
        saveProgressDataAccessObject.saveProgress(inputData.getUsername(),
                    getRoomIDs(inputData.getRoomsUnlocked()),
                    getItemIDs(inputData.getItemInventory()),
                    inputData.getHintsWatched()
        );

        // 3. Perform Session Cleanup
        logoutDataAccessObject.setCurrentUsername(null);

        // 4. Trigger the Saved Logout Success View Cleanly
        SaveAndLogoutOutputData outputData = new SaveAndLogoutOutputData(inputData.getUsername(), false);
        saveAndLogoutPresenter.prepareSavedSuccessView(outputData);
    }

    private ArrayList<String> getItemIDs(ArrayList<Item> inventory) {
        ArrayList<String> itemIDs = new ArrayList<>();
        for (Item item : inventory) {
            if (!itemIDs.contains(item.getId())) {
                itemIDs.add(item.getId());
            }
        }
        return itemIDs;
    }

    private ArrayList<String> getRoomIDs(ArrayList<Room> rooms) {
        ArrayList<String> roomIDs = new ArrayList<>();
        for (Room room : rooms) {
            if (!roomIDs.contains(room.getId())) {
                roomIDs.add(room.getId());
            }
        }
        return roomIDs;
    }
}
