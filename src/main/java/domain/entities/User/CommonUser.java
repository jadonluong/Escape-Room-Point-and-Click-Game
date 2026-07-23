package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The CommonUser class that extends the AbstractUser class and implements the CommonUserFunction interface.
 */
public class CommonUser extends AbstractUser implements CommonUserFunction{
    private final String username;
    private final String password;
    private ArrayList<String> jsonItemInventory = new ArrayList<>();
    private ArrayList<String> jsonRoomsUnlocked = new ArrayList<>();

    public CommonUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void unlockRoom(Room room) {
        if (!roomsUnlocked.contains(room)) {
            this.roomsUnlocked.add(room);
            this.jsonRoomsUnlocked.add(room.getId());
        }
    }

    @Override
    public void saveItem(Item item) {
        if (!this.itemInventory.contains(item)) {
            this.itemInventory.add(item);
            this.jsonItemInventory.add(item.getId());
        }
    }

    @Override
    public void setRoomsUnlockedIDs(ArrayList<String> roomIDs) {
        this.jsonRoomsUnlocked = roomIDs;
    }

    @Override
    public void setItemInventoryIDs(ArrayList<String> itemIDs) {
        this.jsonItemInventory = itemIDs;
    }

    @Override
    public void setHintsWatched(HashMap<String, Integer> hints) {
        this.hintsWatched = hints;
    }

    @Override
    public ArrayList<String> getRoomsUnlockedIDs() {
        return this.jsonRoomsUnlocked;
    }

    @Override
    public ArrayList<String> getItemInventoryIDs() {
        return this.jsonItemInventory;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
