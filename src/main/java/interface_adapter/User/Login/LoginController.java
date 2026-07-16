package interface_adapter.User.Login;

import application.use_cases.User.Login.LoginInputBoundary;
import application.use_cases.User.Login.LoginInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {
    final private LoginInputBoundary userLoginUseCaseInteractor;

    public LoginController(LoginInputBoundary userLoginUseCaseInteractor) {
        this.userLoginUseCaseInteractor = userLoginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(username, password);
        userLoginUseCaseInteractor.execute(loginInputData);
    }
}
