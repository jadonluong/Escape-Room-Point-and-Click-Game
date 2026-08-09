package application.use_cases.user.save_progress;

/**
 * The Output Data for the Save Progress Use Case.
 */
public class SaveProgressOutputData {
    private final String username;
    private final boolean isSaveFailed;

    public SaveProgressOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.isSaveFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSaveFailed() {
        return isSaveFailed;
    }
}
