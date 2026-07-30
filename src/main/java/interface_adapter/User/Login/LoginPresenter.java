package interface_adapter.User.Login;

import application.use_cases.User.Login.LoginOutputBoundary;
import application.use_cases.User.Login.LoginOutputData;
import interface_adapter.User.LoggedIn.LoggedInViewModel;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public LoginPresenter(LoginViewModel loginViewModel, LoggedInViewModel loggedInViewModel) {
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.getState().setErrorMessage(errorMessage);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        loginViewModel.getState().setErrorMessage("");

        loggedInViewModel.getState().setUsername(outputData.getUser().getUsername());
        loggedInViewModel.getState().setRegistered(outputData.getUser().isRegistered());
        loggedInViewModel.firePropertyChanged();
    }
}
