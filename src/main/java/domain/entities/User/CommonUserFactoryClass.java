package domain.entities.User;

import domain.entities.Item.Item;
import domain.entities.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactoryClass implements CommonUserFactory{

    @Override
    public User createCommonUser(String username, String password) {
        return new CommonUser(username, password);
    }

    @Override
    public User restoreCommonUser(String username, String password,

                                  ArrayList<Item> storyItemInventory,
                                  ArrayList<Room> storyRooms,
                                  HashMap<String, Integer> storyHints,

                                  Map<String, ArrayList<Item>> quickItemInventory,
                                  ArrayList<Room> quickRooms,
                                  Map<String, HashMap<String, Integer>> quickHints) {
        CommonUser user = new CommonUser(username, password);
        restoreStoryMode(storyItemInventory, storyRooms, storyHints, user);
        restoreQuickMode(quickItemInventory, quickRooms, quickHints, user);
        return user;
    }

    private static void restoreQuickMode(Map<String, ArrayList<Item>> quickItemInventory,
                                         ArrayList<Room> quickRooms,
                                         Map<String, HashMap<String, Integer>> quickHints,
                                         CommonUser user) {
        user.setActiveGameMode("QuickMode");

        if (quickRooms !=null) {
            for (Room room : quickRooms){
                user.unlockRoom(room);
            }
        }

        if (quickItemInventory != null) {
            for (Map.Entry<String, ArrayList<Item>> entry : quickItemInventory.entrySet()) {
                String roomId = entry.getKey();
                user.saveCurrentRoomID(roomId);
                for (Item item : entry.getValue()) {
                    user.saveItem(item);
                }
            }
        user.saveCurrentRoomID(null);
        }

        if (quickHints != null) {
            user.setQuickModeHintsWatched(quickHints);
        }
        user.setActiveGameMode(null);
    }

    private static void restoreStoryMode(ArrayList<Item> storyItemInventory,
                                         ArrayList<Room> storyRooms,
                                         HashMap<String, Integer> storyHints,
                                         CommonUser user) {
        user.setActiveGameMode("StoryMode");
        if (storyRooms != null) {
            for (Room room : storyRooms) {
                user.unlockRoom(room);
            }
        }

        if (storyItemInventory != null) {
            for (Item item : storyItemInventory) {
                user.saveItem(item);
            }
        }

        if (!storyHints.isEmpty()) {
            user.setStoryModeHintsWatched(storyHints);
        }
        user.setActiveGameMode(null);
    }
}
