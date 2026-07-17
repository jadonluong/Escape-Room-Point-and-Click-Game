package application.use_cases.Hint.GetHint;

/**
 * The input boundary for the Get Hint use case.
 */
public interface GetHintInputBoundary {

    /**
     * Executes the Get Hint use case.
     * @param getHintInputData the input data of the Get Hint use case
     */
    void execute(GetHintInputData getHintInputData);
}
