package interface_adapter.User.Signup;

public class SignupViewModel {
    private final SignupState state = new SignupState();

    public SignupState getState() {
        return state;
    }
}
