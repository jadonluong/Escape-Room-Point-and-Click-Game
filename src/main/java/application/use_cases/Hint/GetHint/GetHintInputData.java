package application.use_cases.Hint.GetHint;

/**
 * The input data for the Get Hint use case.
 */
public class GetHintInputData {
    private final String objectID;

    public GetHintInputData(String objectID) {
        this.objectID = objectID;
    }

    public String getObjectID() {
        return this.objectID;
    }
}
