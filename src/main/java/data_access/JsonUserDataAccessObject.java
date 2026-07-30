package data_access;

import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintUserDataAccessInterface;
import application.use_cases.Interactable.Interact.InteractUserDataAccessInterface;
import application.use_cases.Puzzle.EnterExit.EnterExitUserDataAccessInterface;
import application.use_cases.Puzzle.Solve.SolveUserDataAccessInterface;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUserDataAccessObject implements
        LoginUserDataAccessInterface,
        SignupUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        SaveProgressUserDataAccessInterface,
        GetHintUserDataAccessInterface,
        ActionTriggerDataAccessInterface,
        InteractUserDataAccessInterface,
        EnterExitUserDataAccessInterface,
        SolveUserDataAccessInterface {

    private static final String FILE_PATH = "user_data/users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, UserDataModel> rawUsers;

    private final RoomRegistry roomRegistry;
    private final ItemRegistry itemRegistry;
    private String currentUsername;
    private User currentUser;

    public JsonUserDataAccessObject(RoomRegistry roomRegistry, ItemRegistry itemRegistry) {
        this.roomRegistry = roomRegistry;
        this.itemRegistry = itemRegistry;
        this.rawUsers = load();
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            this.currentUsername = user.getUsername();
        } else {
            this.currentUsername = null;
        }
    }

    @Override
    public User getCurrentUser() {
        if (currentUsername == null || currentUsername.isEmpty()) {
            return null;
        }
        return this.currentUser; // Returns the live, in-memory updated User, guest or common user.
    }


    private Map<String, UserDataModel> load() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                return new HashMap<>();
            }
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Type type = new TypeToken<Map<String, UserDataModel>>() {
            }.getType();
            Map<String, UserDataModel> result = gson.fromJson(json, type);
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
            Files.writeString(path, gson.toJson(rawUsers), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user data to " + FILE_PATH, e);
        }
    }

    @Override
    public boolean existsByName(String username) {
        return rawUsers.containsKey(username);
    }

    // Only called in the signup use case.
    @Override
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot save a null user.");
        }
        if (!(user instanceof CommonUserFunction)) {
            throw new IllegalArgumentException("Cannot save a user with no password.");
        }
        if (!(user instanceof CommonUser incomingUser)) {
            throw new IllegalArgumentException("Unsupported User implementation type.");
        }

        UserDataModel model = convertEntityToDataModel(incomingUser);

        rawUsers.put(model.username, model);
        persist();
    }

    @Override
    public CommonUserFunction getUserPassword(String username) {
        UserDataModel userDTO = rawUsers.get(username);
        if (userDTO == null) {
            throw new IllegalArgumentException("No such user: " + username);
        }
        CommonUser user = new CommonUser(userDTO.username, userDTO.password);
        hydrateUserFromDataModel(user, userDTO);
        return user;
    }

    /**
     * Retrieves the user and converts all JSON String IDs into live Room and Item domain objects
     * for both Story Mode and Quick Mode.
     */
    @Override
    public CommonUser getUser(String username) {
        UserDataModel userDTO = rawUsers.get(username);

        if (userDTO == null) {
            throw new IllegalArgumentException("No such user: " + username);
        }

        CommonUser user = new CommonUser(userDTO.username, userDTO.password);
        // Safety check: ensure runtime memory fields are never null
        user.initializeRuntimeState();

        hydrateUserFromDataModel(user, userDTO);


        /* syncRuntimeFromJSON(user); // Copy transient hint maps & room IDs from ModeProgress into AbstractUser
        hydrateStoryModeLiveObjects(user);
        hydrateQuickModeLiveObjects(user);
         */
        return user;
    }

    private void hydrateUserFromDataModel(CommonUser user, UserDataModel userDTO) {
        if (userDTO.modeProgress == null) return;

        UserDataModel.StoryModeDataDTO smData = userDTO.modeProgress.storyMode;
    }

    /**
     * Syncs deserialized JSON data from ModeProgress into AbstractUser's transient fields.
     */
    private void syncRuntimeFromJSON(CommonUser user) {
        if (user.getModeProgress() != null) {
            // Sync Quick Mode Hints
            if (user.getModeProgress().getQuickMode() != null) {
                Map<String, HashMap<String, Integer>> qmHints = user.getQuickModeHintsWatched();
                if (qmHints != null) {
                    user.setQuickModeHintsWatched(qmHints);
                }
            }

            // Sync Story Mode Hints & Saved Room ID
            if (user.getModeProgress().getStoryMode() != null) {
                HashMap<String, Integer> smHints = user.getStoryModeHintsWatched();
                if (smHints != null) {
                    user.setStoryModeHintsWatched(smHints);
                }

                String savedStoryRoomID = user.getStoryModeCurrentRoomID();
                if (savedStoryRoomID != null) {
                    user.setStoryModeCurrentRoomID(savedStoryRoomID);
                }
            }
        }
    }

    private void hydrateQuickModeLiveObjects(CommonUser user) {
        user.setActiveGameMode("QuickMode");

        // Translate Room text IDs from the JSON file into active game Room objects
        if (user.getQuickModeRoomsUnlockedIDs() != null) {
            for (String roomId : user.getQuickModeRoomsUnlockedIDs()) {
                Room room = roomRegistry.getRoomById(roomId);
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
                    Item item = itemRegistry.getItemById(itemID);
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
                Room room = roomRegistry.getRoomById(roomId);
                if (room != null) {
                    user.unlockRoom(room);
                }
            }
        }

        if (user.getStoryModeItemInventoryIDs() != null) {
            for (String itemId : user.getStoryModeItemInventoryIDs()) {
                Item item = itemRegistry.getItemById(itemId);
                if (item != null) {
                    user.saveItem(item);
                }
            }
        }
        // No hint hydration needed.
        // Gson already populated storyModeHintsWatched and quickModeHintsWatched directly into the user object.
        user.setActiveGameMode(null);
    }

    @Override
    public void saveProgress(CommonUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot save progress. No such user.");
        }
        UserDataModel model = convertEntityToDataModel(user);
        rawUsers.put(model.username, model);
        persist();
    }

    private UserDataModel convertEntityToDataModel(CommonUser user) {
        UserDataModel model = new UserDataModel();
        model.username = user.getUsername();
        model.password = user.getPassword();
        model.modeProgress = new UserDataModel.ModeProgressDTO();
        model.modeProgress.quickMode = new UserDataModel.QuickModeDataDTO();
        model.modeProgress.storyMode = new UserDataModel.StoryModeDataDTO();

        model.modeProgress.storyMode.storyModeCurrentRoomID = user.getStoryModeCurrentRoomID();
        model.modeProgress.storyMode.storyModeRoomsUnlocked = user.getStoryModeRoomsUnlockedIDs();
        model.modeProgress.storyMode.storyModeItemInventory = user.getStoryModeItemInventoryIDs();
        model.modeProgress.storyMode.storyModeHintsWatched = user.getStoryModeHintsWatched();

        model.modeProgress.quickMode.quickModeRoomsUnlocked = user.getQuickModeRoomsUnlockedIDs();
        model.modeProgress.quickMode.quickModeItemInventory = user.getQuickModeItemInventoryIDs();
        model.modeProgress.quickMode.quickModeHintsWatched = user.getQuickModeHintsWatched();

        return model;
    }

    public UserDataModel getRawUserData(String username) {
        return rawUsers.get(username);
    }


    public class UserDataModel {
        public String username;
        public String password;
        public ModeProgressDTO modeProgress;

        public static class ModeProgressDTO {
            public QuickModeDataDTO quickMode;
            public StoryModeDataDTO storyMode;
        }

        public static class QuickModeDataDTO {
            public List<String> quickModeRoomsUnlocked = new ArrayList<>();
            public Map<String, ArrayList<String>> quickModeItemInventory = new HashMap<>();
            public Map<String, HashMap<String, Integer>> quickModeHintsWatched = new HashMap<>();
        }

        public static class StoryModeDataDTO {
            public String storyModeCurrentRoomID;
            public List<String> storyModeRoomsUnlocked = new ArrayList<>();
            public List<String> storyModeItemInventory = new ArrayList<>();
            public HashMap<String, Integer> storyModeHintsWatched = new HashMap<>();
        }
    }


}
