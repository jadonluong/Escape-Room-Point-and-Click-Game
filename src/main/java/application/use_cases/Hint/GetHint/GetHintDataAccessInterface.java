package application.use_cases.Hint.GetHint;

/**
 * The DAO for the Get Hint use case.
 */
public interface GetHintDataAccessInterface {

    /**
     * Returns the hint message related to the given object with the given request count.
     * @param objectID the ID of the object the hint message is related to
     * @param requestCount the request count for getting progressive hints, 0 if this is the first request
     * @return
     */
    String getHintMessageForRequestCount(String objectID, int requestCount);

    /**
     * Checks if an object can access a hint.
     * @param objectID the ID of the object
     * @return true if the object has a hint related to it, false otherwise
     */
    Boolean existByObjectID(String objectID);
}
