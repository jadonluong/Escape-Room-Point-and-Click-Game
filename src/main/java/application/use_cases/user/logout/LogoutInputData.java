package application.use_cases.user.logout;

/**
 * The Input Data for the Logout Use Case.
 */
public class LogoutInputData {
    private String username;

    public LogoutInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return this.username;
    }
}
