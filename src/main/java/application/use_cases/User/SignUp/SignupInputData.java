package application.use_cases.User.SignUp;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public SignupInputData(String username, String pwd, String repeatedPwd) {
        this.username = username;
        this.password = pwd;
        this.repeatedPassword = repeatedPwd;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRepeatedPassword() {
        return this.repeatedPassword;
    }
}
