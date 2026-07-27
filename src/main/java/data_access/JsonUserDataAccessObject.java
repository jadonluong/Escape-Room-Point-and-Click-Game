package data_access;

import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.User.Login.LoginUserDataAccessInterface;
import application.use_cases.User.SaveProgress.SaveProgressUserDataAccessInterface;
import application.use_cases.User.SignUp.SignupUserDataAccessInterface;
import application.use_cases.User.Logout.LogoutUserDataAccessInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.Item.Item;
import domain.entities.Room.Room;
import domain.entities.User.CommonUser;
import domain.entities.User.CommonUserFunction;
import domain.entities.User.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonUserDataAccessObject implements
        LoginUserDataAccessInterface,
        SignupUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        SaveProgressUserDataAccessInterface {

    private static final String FILE_PATH = "data/users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, CommonUser> users;

    private final RoomRegistry roomRegistry;
    private final ItemRegistry itemRegistry;
    private String currentUsername;

    public JsonUserDataAccessObject(RoomRegistry roomRegistry, ItemRegistry itemRegistry) {
        this.roomRegistry = roomRegistry;
        this.itemRegistry = itemRegistry;
        this.users = load();
    }

    private Map<String, CommonUser> load() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                return new HashMap<>();
            }
            String json = Files.readString(path);
            Type type = new TypeToken<Map<String, CommonUser>>() {}.getType();
            Map<String, CommonUser> result = gson.fromJson(json, type);
            return result != null ? result : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load user data from " + FILE_PATH, e);
        }
    }

    private void persist() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, gson.toJson(users));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user data to " + FILE_PATH, e);
        }
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    // Only called in the signup use case.
    @Override
    public void save(User user) {
        if (!(user instanceof CommonUserFunction)) {
            throw new IllegalArgumentException("Cannot save a user with no password.");
        }
        if (!(user instanceof CommonUser incomingUser)) {
            throw new IllegalArgumentException("Unsupported User implementation type.");
        }

        String username = user.getUsername();

        users.put(username, incomingUser);
        persist();
    }

    @Override
    public CommonUserFunction getUserPassword(String username) {
        CommonUser user = users.get(username);
        if (user == null) {
            throw new IllegalArgumentException("No such user: " + username);
        }
        return user;
    }

    /**
     * Retrieves the user and converts all JSON String IDs into live Room and Item domain objects
     * for both Story Mode and Quick Mode.
     */
    @Override
    public CommonUser getUser(String username) {
        CommonUser user = users.get(username);

        if (user == null) {
            throw new IllegalArgumentException("No such user: " + username);
        }

        // Safety check: ensure runtime memory fields are never null
        user.initializeRuntimeState();

        hydrateStoryModeLiveObjects(user);
        hydrateQuickModeLiveObjects(user);

        return user;
    }

    private void hydrateQuickModeLiveObjects(CommonUser user) {
        user.setActiveGameMode("QuickMode");

        // Translate Room text IDs from the JSON file into active game Room objects
        if (user.getQuickModeRoomsUnlockedIDs() != null) {
            for (String roomId : user.getQuickModeRoomsUnlockedIDs()) {
                Room room = roomRegistry.getRoomByID(roomId);
                if (room != null) {
                    user.unlockRoom(room);
                }
            }
        }

        // Translate Item text IDs from the JSON file into active game Item objects
        if (user.getQuickModeItemInventoryIDs() != null) {
            for (Map.Entry<String, ArrayList<String>> entry : user.getQuickModeItemInventoryIDs().entrySet()) {
                String roomID = entry.getKey();
                user.saveCurrentRoomID(roomID);

                // Instantiate every item collected in the room with roomID
                for (String itemID : entry.getValue()) {
                    Item item = itemRegistry.getItemByID(itemID);
                    if (item != null) {
                        user.saveItem(item);
                    }
                }
            }
        }
        // Hints require no work here; Gson has already restored the hintsWatched map safely
        user.saveCurrentRoomID(null);
        user.setActiveGameMode(null);
    }

    private void hydrateStoryModeLiveObjects(CommonUser user) {
        user.setActiveGameMode("StoryMode");
        if (user.getStoryModeRoomsUnlockedIDs() != null) {
            for (String roomId : user.getStoryModeRoomsUnlockedIDs()) {
                Room room = roomRegistry.getRoomByID(roomId);
                if (room != null) {
                    user.unlockRoom(room);
                }
            }
        }

        if (user.getStoryModeItemInventoryIDs() != null) {
            for (String itemId : user.getStoryModeItemInventoryIDs()) {
                Item item = itemRegistry.getItemByID(itemId);
                if (item != null) {
                    user.saveItem(item);
                }
            }
        }
        // No hint hydration needed.
        // Gson already populated storyModeHintsWatched and quickModeHintsWatched directly into the user object.
        user.setActiveGameMode(null);
    }

    // TODO: update saveProgress with new user structure
    @Override
    public void saveProgress(String username,
                             ArrayList<String> roomIDs,
                             ArrayList<String> itemIDs,
                             HashMap<String, Integer> hints) {
        CommonUser user = users.get(username);

        if (user == null) {
            throw new IllegalArgumentException("Cannot save progress. No such user: " + username);
        }

        user.setQuickModeRoomsUnlockedIDs(roomIDs);
        user.setStoryModeItemInventoryIDs(itemIDs);
        user.setStoryModeHintsWatched(hints);
        persist();
    }



    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

}