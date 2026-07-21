package interface_adapter.User.Login;

public class LoginViewModel {

    private final LoginState state = new LoginState();

    public LoginState getState() {
        return state;
    }

    public void setError(String errorMessage) {
        state.setErrorMessage(errorMessage);
    }
}
