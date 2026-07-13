package application.use_cases.User.Login;

import domain.entities.User.User;

/**
 * The output data of the Login Use Case.
 */
public class LoginOutputData {
    private final User commonUser;
    private final boolean isloginFailed;

    public LoginOutputData(User user, boolean isloginFailed) {
        this.commonUser = user;
        this.isloginFailed = isloginFailed;
    }

    public User getUser() {
        return this.commonUser;
    }
}
