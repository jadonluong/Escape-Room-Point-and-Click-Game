package application.use_cases.User.SignUp;

/**
 * Input Boundary for actions related to signing up.
 */
public interface SignUpInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void executeSignup(SignupInputData signupInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}
