package interface_adapter.user.signup;

import application.use_cases.user.signup.SignupInputBoundary;
import application.use_cases.user.signup.SignupInputData;

/**
 * The controller for the Signup Use Case.
 */
public class SignupController {
    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param username the username to sign up
     * @param password the password
     * @param repeatedPassword the password repeated
     */
    public void execute(String username, String password, String repeatedPassword) {
        final SignupInputData signupInputData = new SignupInputData(username, password, repeatedPassword);
        userSignupUseCaseInteractor.executeSignup(signupInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView() {
        userSignupUseCaseInteractor.switchToLoginView();
    }
}
