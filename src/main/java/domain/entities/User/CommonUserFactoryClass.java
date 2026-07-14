package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactoryClass implements CommonUserFactory{

    @Override
    public User createCommonUser(String username, String password) {
        return new CommonUser(username, password);
    }

    @Override
    public User restoreCommonUser(String username, String password, ArrayList<Item> itemInventory, ArrayList<Room> rooms) {
        CommonUser user = new CommonUser(username, password);

        if (rooms != null) {
            for (Room room : rooms) {
                user.unlockRoom(room);
            }
        }

        if (itemInventory != null) {
            for (Item item : itemInventory) {
                user.saveItem(item);
            }
        }

        return user;
    }
}
