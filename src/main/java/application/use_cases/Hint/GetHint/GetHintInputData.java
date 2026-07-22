package application.use_cases.Hint.GetHint;

import domain.entities.User.User;

/**
 * The input data for the Get Hint use case.
 */
public class GetHintInputData {
    private final String objectID;
    private final User currentUser;

    public GetHintInputData(String objectID, User user) {
        this.objectID = objectID;
        this.currentUser = user;
    }

    public String getObjectID() {
        return this.objectID;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
