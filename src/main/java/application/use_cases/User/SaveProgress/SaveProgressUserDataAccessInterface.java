package application.use_cases.User.SaveProgress;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

public interface SaveProgressUserDataAccessInterface {

    /**
     * Saves the common user's progress.
     * @param Username the username of the common user
     * @param rooms the rooms the common user has unlocked
     * @param items the items the common user has collected
     */
    void saveProgress(String Username, ArrayList<Room> rooms, ArrayList<Item> items);
}
