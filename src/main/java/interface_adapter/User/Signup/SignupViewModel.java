package interface_adapter.User.Signup;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {

    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }
}