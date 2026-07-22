package interface_adapter.User.SaveProgress;

import application.use_cases.User.SaveProgress.SaveProgressInputBoundary;
import application.use_cases.User.SaveProgress.SaveProgressInputData;
import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The controller for the Save Progress Use Case.
 */
public class SaveProgressController {
    private final SaveProgressInputBoundary saveProgressInteractor;

    public SaveProgressController(SaveProgressInputBoundary saveProgressInputBoundary) {
        this.saveProgressInteractor = saveProgressInputBoundary;
    }

    /**
     * Executes the Save Progress Use case.
     * @param username the username of the user
     * @param rooms the rooms the user has unlocked
     * @param items the items the user has collected
     * @param hintsWatched the hints the user has watched
     * @param isRegistered the status of the user, true if the user is a common user, false if the user is a guest user
     */
    public void execute(String username,
                        ArrayList<Room> rooms,
                        ArrayList<Item> items,
                        HashMap<String, Integer> hintsWatched,
                        boolean isRegistered) {
        SaveProgressInputData inputData = new SaveProgressInputData(username, rooms, items, hintsWatched, isRegistered);
        this.saveProgressInteractor.execute(inputData);
    }
}
