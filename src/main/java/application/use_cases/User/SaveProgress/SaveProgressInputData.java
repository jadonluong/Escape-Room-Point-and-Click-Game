package application.use_cases.User.SaveProgress;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * The Input Data for the Save Progress Use Case.
 */
public class SaveProgressInputData {
    private String username;
    private ArrayList<Room> roomsUnlocked;
    private ArrayList<Item> itemInventory;
    private Boolean isRegistered;

    public SaveProgressInputData(String username, ArrayList<Room> rooms, ArrayList<Item> items, boolean isRegistered){
        this.username = username;
        this.roomsUnlocked = rooms;
        this.itemInventory = items;
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

    public Boolean getRegisteredStatus() {
        return isRegistered;
    }
}
