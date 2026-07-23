package data_access;

import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.User.Login.LoginUserDataAccessInterface;
import application.use_cases.User.SaveProgress.SaveProgressUserDataAccessInterface;
import application.use_cases.User.SignUp.SignupUserDataAccessInterface;
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
        SaveProgressUserDataAccessInterface {

    private static final String FILE_PATH = "data/users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, CommonUser> users;

    private final RoomRegistry roomRegistry;
    private final ItemRegistry itemRegistry;

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
        if (!(user instanceof CommonUser)) {
            throw new IllegalArgumentException("Unsupported User implementation type.");
        }

        String username = user.getUsername();
        CommonUser incomingUser = (CommonUser) user;

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

    @Override
    public User getUser(String username) {
        CommonUser user = users.get(username);

        user.getRoomsUnlocked().clear();
        user.getItemInventory().clear();

        // Translate Room text IDs from the JSON file into active game Room objects
        if (user.getRoomsUnlockedIDs() != null) {
            for (String roomId : user.getRoomsUnlockedIDs()) {
                Room room = roomRegistry.getRoomByID(roomId);
                if (room != null) {
                    // Leverages the built-in unlockRoom method inside AbstractUser to fill lists cleanly
                    user.unlockRoom(room);
                }
            }
        }

        // Translate Item text IDs from the JSON file into active game Item objects
        if (user.getItemInventoryIDs() != null) {
            for (String itemId : user.getItemInventoryIDs()) {
                Item item = itemRegistry.getItemByID(itemId);
                if (item != null) {
                    // Leverages the built-in saveItem method inside AbstractUser to fill lists cleanly
                    user.saveItem(item);
                }
            }
        }

        // Hints require no work here; Gson has already restored the hintsWatched map safely
        return user;
    }

    @Override
    public void saveProgress(String username,
                             ArrayList<String> roomIDs,
                             ArrayList<String> itemIDs,
                             HashMap<String, Integer> hints) {
        CommonUser user = users.get(username);

        if (user == null) {
            throw new IllegalArgumentException("Cannot save progress. No such user: " + username);
        }

        user.setRoomsUnlockedIDs(roomIDs);
        user.setItemInventoryIDs(itemIDs);
        user.setHintsWatched(hints);
        persist();
    }
}