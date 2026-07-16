package application.use_cases.User.Login;

/**
 * Input Boundary for Login Use Case.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);
}
