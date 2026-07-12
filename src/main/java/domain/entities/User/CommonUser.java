package domain.entities.User;

/**
 * The CommonUser class that extends the AbstractUser class.
 */
public class CommonUser extends AbstractUser implements User{
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

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
