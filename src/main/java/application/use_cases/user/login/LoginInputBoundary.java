package application.use_cases.user.login;

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
