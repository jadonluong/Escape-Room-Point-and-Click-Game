package application.use_cases.user.save_and_logout;

/**
 * The output data for the Save and Logout use case.
 */
public class SaveAndLogoutOutputData {
    private String username;
    private boolean useCaseFailed;

    public SaveAndLogoutOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
}
