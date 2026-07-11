package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.List;

public class CommonUser implements User{
    private String username;
    private String password;
    private List<Item> inventory;
    private List<Room> roomsUnlocked;

    public CommonUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
