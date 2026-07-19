package application.use_cases.User.SaveProgress;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * The interactor for the Save Progress Use Case.
 */
public class SaveProgressInteractor implements SaveProgressInputBoundary{
    private final SaveProgressOutputBoundary saveProgressPresenter;
    private final SaveProgressUserDataAccessInterface saveProgressUserDataAccessObject;

    public SaveProgressInteractor(SaveProgressOutputBoundary saveProgressOutputBoundary,
                                  SaveProgressUserDataAccessInterface saveProgressUserDataAccessInterface) {
        this.saveProgressPresenter = saveProgressOutputBoundary;
        this.saveProgressUserDataAccessObject = saveProgressUserDataAccessInterface;
    }

    @Override
    public void execute(SaveProgressInputData saveProgressInputData) {
        if (!saveProgressInputData.getRegisteredStatus()) {
            saveProgressPresenter.prepareFailView("User is in Guest Mode, progress cannot be saved.");
        }
        else {
            saveProgressUserDataAccessObject.saveProgress(saveProgressInputData.getUsername(),
                    getRoomIDs(saveProgressInputData),
                    getItemIDs(saveProgressInputData));

            final SaveProgressOutputData saveProgressOutputData = new SaveProgressOutputData(
                    saveProgressInputData.getUsername(),
                    false);

            saveProgressPresenter.prepareSuccessView(saveProgressOutputData);
        }
    }

    private static ArrayList<String> getItemIDs(SaveProgressInputData saveProgressInputData) {
        ArrayList<String> itemIDs = new ArrayList<>();
        for (Item item : saveProgressInputData.getItemInventory()) {
            if (!itemIDs.contains(item.getId())) {
                itemIDs.add(item.getId());
            }
        }
        return itemIDs;
    }

    private static ArrayList<String> getRoomIDs(SaveProgressInputData saveProgressInputData) {
        ArrayList<String> roomIDs = new ArrayList<>();
        for (Room room : saveProgressInputData.getRoomsUnlocked()) {
            if (!roomIDs.contains(room.getId())) {
                roomIDs.add(room.getId());
            }
        }
        return roomIDs;
    }
}
