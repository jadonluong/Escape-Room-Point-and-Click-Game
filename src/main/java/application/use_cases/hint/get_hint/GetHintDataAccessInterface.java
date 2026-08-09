package application.use_cases.hint.get_hint;

import domain.entities.hint.Hint;

/**
 * The DAO for the Get Hint use case.
 */
public interface GetHintDataAccessInterface {

    /**
     * Returns the full list of progressive hints for an object.
     * @param objectID the ID of the object the user clicked on to get hint
     * @return the list of progressive hints for the object with objectID
     */
    Hint getHintForObjectID(String objectID);

    /**
     * Checks if an object can access a hint.
     * @param objectID the ID of the object
     * @return true if the object has a hint related to it, false otherwise
     */
    Boolean existByObjectID(String objectID);
}
