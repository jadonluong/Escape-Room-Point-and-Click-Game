package interface_adapter.user.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
