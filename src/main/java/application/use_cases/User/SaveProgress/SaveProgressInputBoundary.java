package application.use_cases.User.SaveProgress;

/**
 * Input Boundary for the Save Progress Use Case.
 */
public interface SaveProgressInputBoundary {

    /**
     * Executes the Save Progress Use Case.
     * @param saveProgressInputData
     */
    void execute(SaveProgressInputData saveProgressInputData);
}
