package data_access;

import application.game_registry.ItemRegistry;
import application.game_registry.RoomRegistry;
import application.use_cases.user.login.LoginUserDataAccessInterface;
import application.use_cases.user.save_progress.SaveProgressUserDataAccessInterface;
import application.use_cases.user.signup.SignupUserDataAccessInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.entities.item.Item;
import domain.entities.room.Room;
import domain.entities.user.CommonUser;
import domain.entities.user.CommonUserFunction;
import domain.entities.user.User;

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
        SaveProgressUserDataAccessInterface {

    private static final String FILE_PATH = "user_data/users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, UserDataModel> rawUsers;

    private final RoomRegistry roomRegistry;
    private final ItemRegistry itemRegistry;

    public JsonUserDataAccessObject(RoomRegistry roomRegistry, ItemRegistry itemRegistry) {
        this.roomRegistry = roomRegistry;
        this.itemRegistry = itemRegistry;
        this.rawUsers = load();
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
            throw new IllegalArgumentException("Unsupported user implementation type.");
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

        final CommonUser user = new CommonUser(userDTO.username, userDTO.password);
        // Safety check: ensure runtime memory fields are never null
        user.initializeRuntimeState();
        hydrateUserFromDataModel(user, userDTO);

        return user;
    }
    
    private void hydrateUserFromDataModel(CommonUser user, UserDataModel userDTO) {
        if (userDTO.modeProgress == null) {
            return;
        }

        hydrateStoryModeFromDTO(user, userDTO);

        hydrateQuickModeFromDTO(user, userDTO);
    }

    private void hydrateQuickModeFromDTO(CommonUser user, UserDataModel userDTO) {
        user.setActiveGameMode("QuickMode");
        UserDataModel.QuickModeDataDTO qmData = userDTO.modeProgress.quickMode;
        if (qmData != null) {
            for (String roomId : qmData.quickModeRoomsUnlocked) {
                final Room room = roomRegistry.getRoomById(roomId);
                if (room != null && !user.getRoomsUnlocked().contains(room)) {
                    user.unlockRoom(room);
                }
            }

            for (Map.Entry<String, ArrayList<String>> entry : qmData.quickModeItemInventory.entrySet()) {
                final String roomID = entry.getKey();
                user.saveCurrentRoomID(roomID);

                // Instantiate every item collected in the room with roomID
                for (String itemID : entry.getValue()) {
                    final Item item = itemRegistry.getItemById(itemID);
                    if (item != null && !user.getItemInventory().contains(item)) {
                        user.saveItem(item);
                    }
                }
            }
            if (qmData.quickModeHintsWatched != null) {
                user.setQuickModeHintsWatched(new HashMap<>(qmData.quickModeHintsWatched));
            }
        }
        user.saveCurrentRoomID(null);
        user.setActiveGameMode(null);
    }

    private void hydrateStoryModeFromDTO(CommonUser user, UserDataModel userDTO) {
        user.setActiveGameMode("StoryMode");
        final UserDataModel.StoryModeDataDTO smData = userDTO.modeProgress.storyMode;
        if (smData != null) {
            if (smData.storyModeCurrentRoomID != null) {
                user.setStoryModeCurrentRoomID(smData.storyModeCurrentRoomID);
            }

            for (String roomId : smData.storyModeRoomsUnlocked) {
                final Room room = roomRegistry.getRoomById(roomId);
                if (room != null && !user.getRoomsUnlocked().contains(room)) {
                    user.unlockRoom(room);
                }
            }

            for (String itemId : smData.storyModeItemInventory) {
                final Item item = itemRegistry.getItemById(itemId);
                if (item != null && !user.getItemInventory().contains(item)) {
                    user.saveItem(item);
                }
            }

            if (smData.storyModeHintsWatched != null) {
                user.setStoryModeHintsWatched(new HashMap<>(smData.storyModeHintsWatched));
            }

            if (smData.storyModeInteractables != null) {
                user.setStoryModeInteractables(new ArrayList<>(smData.storyModeInteractables));
            }
        }
        user.setActiveGameMode(null);
    }

    @Override
    public void saveProgress(CommonUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot save progress. No such user.");
        }
        final UserDataModel model = convertEntityToDataModel(user);
        rawUsers.put(model.username, model);
        persist();
    }

    private UserDataModel convertEntityToDataModel(CommonUser user) {
        final UserDataModel model = new UserDataModel();
        model.username = user.getUsername();
        model.password = user.getPassword();
        model.modeProgress = new UserDataModel.ModeProgressDTO();
        model.modeProgress.quickMode = new UserDataModel.QuickModeDataDTO();
        model.modeProgress.storyMode = new UserDataModel.StoryModeDataDTO();

        model.modeProgress.storyMode.storyModeCurrentRoomID = user.getStoryModeCurrentRoomID();
        model.modeProgress.storyMode.storyModeRoomsUnlocked = user.getStoryModeRoomsUnlockedIds();
        model.modeProgress.storyMode.storyModeItemInventory = user.getStoryModeItemInventoryIds();
        model.modeProgress.storyMode.storyModeHintsWatched = user.getStoryModeHintsWatched();
        model.modeProgress.storyMode.storyModeInteractables = user.getStoryModeInteractables();

        model.modeProgress.quickMode.quickModeRoomsUnlocked = user.getQuickModeRoomsUnlockedIds();
        model.modeProgress.quickMode.quickModeItemInventory = user.getQuickModeItemInventoryIds();
        model.modeProgress.quickMode.quickModeHintsWatched = user.getQuickModeHintsWatched();

        return model;
    }

    private static final class UserDataModel {
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
            public List<String> storyModeInteractables = new ArrayList<>();
        }
    }
}
