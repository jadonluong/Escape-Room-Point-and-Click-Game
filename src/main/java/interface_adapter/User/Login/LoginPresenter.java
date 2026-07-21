package interface_adapter.User.Login;

import application.use_cases.User.Login.LoginOutputBoundary;
import application.use_cases.User.Login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;

    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.setError(errorMessage);
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        loginViewModel.getState().setErrorMessage("");
    }
}