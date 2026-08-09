package application.use_cases.user.login;

import domain.entities.user.User;

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

    public Boolean getLoginStatus() {
        return this.isLoginFailed;
    }
}
