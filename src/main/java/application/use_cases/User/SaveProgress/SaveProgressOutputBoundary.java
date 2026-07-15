package application.use_cases.User.SaveProgress;

/**
 * The Output Boundary for the Save Progress Use Case.
 */
public interface SaveProgressOuputBoundary {

    /**
     * Prepares the success view for the Save Progress Use Case.
     * @param saveProgressOuputBoundary the output data of the save progress use case
     */
    void prepareSuccessView(SaveProgressOuputBoundary saveProgressOuputBoundary);

    /**
     * Prepares the fail view for the Save Progress Use Case.
     * @param errorMessage the error message to be displayed.
     */
    void prepareFailView(String errorMessage);
}
