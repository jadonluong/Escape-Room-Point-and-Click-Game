package application.use_cases.Hint.GetHint;

/**
 * The input data for the Get Hint use case.
 */
public class GetHintInputData {
    private String objectID;
    private int requestCount;

    public GetHintInputData(String objectID, int requestCount) {
        this.objectID = objectID;
        this.requestCount = requestCount;
    }

    public String getObjectID() {
        return this.objectID;
    }

    public int getRequestCount() {
        return this.requestCount;
    }
}
