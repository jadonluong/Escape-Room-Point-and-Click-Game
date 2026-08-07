package application.use_cases.User.SaveProgress;

/**
 * Input Boundary for the Save Progress Use Case.
 */
public interface SaveProgressInputBoundary {

    /**
     * Executes the Save Progress Use Case.
     * @param saveProgressInputData the input data of the save progress use case
     */
    void execute(SaveProgressInputData saveProgressInputData);
}
