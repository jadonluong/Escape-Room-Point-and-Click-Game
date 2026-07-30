package application.use_cases.Hint.GetHint;

import domain.entities.User.User;

/**
 * The input data for the Get Hint use case.
 */
public class GetHintInputData {
    private final String objectID;
    private final String currentUserId;

    public GetHintInputData(String objectID, String userId) {
        this.objectID = objectID;
        this.currentUserId = userId;
    }

    public String getObjectID() {
        return this.objectID;
    }

    public String getCurrentUserId() {
        return this.currentUserId;
    }
}
