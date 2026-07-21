package application.use_cases.User.SaveAndLogout;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The input data for the Save and Logout use case.
 */
public class SaveAndLogoutInputData {
    private final String username;
    private final ArrayList<Room> roomsUnlocked;
    private final ArrayList<Item> itemInventory;
    private final HashMap<String, Integer> hintsWatched;
    private final boolean isRegistered;

    public SaveAndLogoutInputData(String username,
                                  ArrayList<Room> roomsUnlocked,
                                  ArrayList<Item> itemInventory,
                                  HashMap<String, Integer> hintsWatched,
                                  boolean isRegistered) {
        this.username = username;
        this.roomsUnlocked = roomsUnlocked;
        this.itemInventory = itemInventory;
        this.hintsWatched = hintsWatched;
        this.isRegistered = isRegistered;
    }

    public String getUsername() { return username; }

    public ArrayList<Room> getRoomsUnlocked() { return roomsUnlocked; }

    public ArrayList<Item> getItemInventory() { return itemInventory; }

    public HashMap<String, Integer> getHintsWatched() { return hintsWatched; }

    public boolean getRegisteredStatus() { return isRegistered; }
}
