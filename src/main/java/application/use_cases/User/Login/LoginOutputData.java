package application.use_cases.User.Login;

import domain.entities.User.User;

/**
 * The output data of the Login Use Case.
 */
public class LoginOutputData {
    private final User commonUser;
    private final boolean isLoginFailed;

    public LoginOutputData(User user, boolean isLoginFailed) {
        this.commonUser = user;
        this.isLoginFailed = isLoginFailed;
    }

    public User getUser() {
        return this.commonUser;
    }
}
