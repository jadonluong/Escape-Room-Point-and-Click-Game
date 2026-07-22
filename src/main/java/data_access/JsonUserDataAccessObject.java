package data_access;

import application.use_cases.User.Login.LoginUserDataAccessInterface;
import application.use_cases.User.SignUp.SignupUserDataAccessInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entities.User.CommonUser;
import domain.entities.User.CommonUserFunction;
import domain.entities.User.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonUserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {

    private static final String FILE_PATH = "data/users.json";
    private final Gson gson = new Gson();

    private final Map<String, String> credentials; // username -> password

    public JsonUserDataAccessObject() {
        this.credentials = load();
    }

    private Map<String, String> load() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                return new HashMap<>();
            }
            String json = Files.readString(path);
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> result = gson.fromJson(json, type);
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
            Files.writeString(path, gson.toJson(credentials));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user data to " + FILE_PATH, e);
        }
    }

    @Override
    public boolean existsByName(String username) {
        return credentials.containsKey(username);
    }

    @Override
    public void save(User user) {
        if (!(user instanceof CommonUserFunction)) {
            throw new IllegalArgumentException("Cannot save a user with no password.");
        }
        String password = ((CommonUserFunction) user).getPassword();
        credentials.put(user.getUsername(), password);
        persist();
    }

    @Override
    public CommonUserFunction getUserPassword(String username) {
        String password = credentials.get(username);
        if (password == null) {
            throw new IllegalArgumentException("No such user: " + username);
        }
        return () -> password;
    }

    @Override
    public User getUser(String username) {
        String password = credentials.get(username);
        return password == null ? null : new CommonUser(username, password);
    }
}