package application.use_cases.Hint.GetHint;

/**
 * Input data for the Get Hint use case.
 */
public class GetHintInputData {
    private final String objectID;

    /**
     * Creates input data for retrieving a hint associated with an object.
     *
     * @param objectID the ID of the object for which a hint is requested
     */
    public GetHintInputData(String objectID) {
        this.objectID = objectID;
    }

    /**
     * Returns the ID of the object associated with the hint request.
     *
     * @return the object ID
     */
    public String getObjectID() {
        return this.objectID;
    }
}
