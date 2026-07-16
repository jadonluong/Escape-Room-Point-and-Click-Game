package application.use_cases.User.Logout;

/**
 * The Input Data for the Logout Use Case.
 */
public class LogoutInputData {
    private String username;
    private boolean toSave;

    public LogoutInputData(String username, boolean toSave) {
        this.username = username;
        this.toSave = toSave;
    }

    String getUsername() {
        return this.username;
    }

    boolean getSaveProgress() {
        return this.toSave;
    }
}
