package application.use_cases.crafting;

/**
 * Input boundary for executing the crafting use case.
 */
public interface CraftingInputBoundary {

    /**
     * Executes the crafting use case with the provided input data.
     *
     * @param craftingInputData the input data required to perform the crafting operation
     */
    void execute(CraftingInputData craftingInputData);
}
