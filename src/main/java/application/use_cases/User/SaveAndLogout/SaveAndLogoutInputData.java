package application.use_cases.User.SaveAndLogout;

/**
 * The input data for the Save and Logout use case.
 */
public class SaveAndLogoutInputData {
    private final String username;

    public SaveAndLogoutInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
