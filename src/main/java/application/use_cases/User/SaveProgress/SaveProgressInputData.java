package application.use_cases.User.SaveProgress;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Input Data for the Save Progress Use Case.
 */
public class SaveProgressInputData {
    private String username;
    private ArrayList<Room> roomsUnlocked;
    private ArrayList<Item> itemInventory;
    private HashMap<String, Integer> hintsWatched;
    private Boolean isRegistered;

    public SaveProgressInputData(String username,
                                 ArrayList<Room> rooms,
                                 ArrayList<Item> items,
                                 HashMap<String, Integer> hintsWatched,
                                 boolean isRegistered){
        this.username = username;
        this.roomsUnlocked = rooms;
        this.itemInventory = items;
        this.hintsWatched = hintsWatched;
        this.isRegistered = isRegistered;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<Room> getRoomsUnlocked() {
        return roomsUnlocked;
    }

    public ArrayList<Item> getItemInventory() {
        return itemInventory;
    }

    public HashMap<String, Integer> getHintsWatched() {
        return hintsWatched;
    }

    public Boolean getRegisteredStatus() {
        return isRegistered;
    }
}
