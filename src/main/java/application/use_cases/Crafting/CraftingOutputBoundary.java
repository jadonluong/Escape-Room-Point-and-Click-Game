package application.use_cases.Crafting;

/**
 * Output boundary for presenting the result of the crafting use case.
 */
public interface CraftingOutputBoundary {

    /**
     * Prepares the success view with the result of a successful crafting operation.
     *
     * @param resultData the output data containing the crafting result
     */
    void prepareSuccessView(CraftingOutputData resultData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param error the error message describing why the crafting operation failed
     */
    void prepareFailView(String error);
}
