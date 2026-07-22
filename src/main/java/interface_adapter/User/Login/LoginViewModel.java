package interface_adapter.User.Login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}