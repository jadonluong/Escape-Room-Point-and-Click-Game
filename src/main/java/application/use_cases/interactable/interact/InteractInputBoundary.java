package application.use_cases.interactable.interact;

/**
 * Input boundary for the Interact use case.
 */
public interface InteractInputBoundary {

    /**
     * Executes an interaction using the provided input data.
     *
     * @param inputData the input data containing information about the interaction
     */
    void interact(InteractInputData inputData);
}
