package domain.entities.User;

/**
 * The CommonUser class that extends the AbstractUser class and implements the CommonUserFunction interface.
 */
public class CommonUser extends AbstractUser implements CommonUserFunction{
    private String username;
    private String password;

    public CommonUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
