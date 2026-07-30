package application.use_cases.Item.PickUp;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.HashMap;
import java.util.Map;

public class PickUpDataAccessInterface {
    public Map<String, Room> rooms = new HashMap<>();
    public Map<String, Item> items = new HashMap<>();
}
