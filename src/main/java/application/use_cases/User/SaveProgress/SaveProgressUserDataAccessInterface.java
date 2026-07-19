package application.use_cases.User.SaveProgress;

import java.util.ArrayList;

public interface SaveProgressUserDataAccessInterface {

    /**
     * Saves the common user's progress.
     * @param Username the username of the common user
     * @param roomIDs the IDs of the rooms the common user has unlocked
     * @param itemIDs the IDs of the items the common user has collected
     */
    void saveProgress(String Username, ArrayList<String> roomIDs, ArrayList<String> itemIDs);
}
