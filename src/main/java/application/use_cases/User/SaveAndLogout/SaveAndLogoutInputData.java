package application.use_cases.User.SaveAndLogout;

import domain.entities.User.User;

/**
 * The input data for the Save and Logout use case.
 */
public class SaveAndLogoutInputData {
    private final User user;

    public SaveAndLogoutInputData(User user) {
        this.user = user;
    }

    public User getUser() { return this.user; }
}
