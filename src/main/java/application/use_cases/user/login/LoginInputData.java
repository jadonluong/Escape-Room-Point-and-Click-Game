package application.use_cases.user.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {
    private String username;
    private String password;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
