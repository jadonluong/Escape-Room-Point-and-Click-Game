package domain.entities.User;

public interface UserFactory {
    User create(String username, String password);
}
