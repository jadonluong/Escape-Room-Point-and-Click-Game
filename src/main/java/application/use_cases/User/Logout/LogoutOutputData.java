package application.use_cases.User.Logout;

/**
 * The output data of the Logout Use Case.
 */
public class LogoutOutputData {
    private final String username;
    private final boolean isLogoutFailed;

    public LogoutOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.isLogoutFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLogoutFailed() {
        return isLogoutFailed;
    }
}
