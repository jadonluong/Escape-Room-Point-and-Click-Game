package application.use_cases.Hint.GetHint;

import java.util.List;

/**
 * The DAO for the Get Hint use case.
 */
public interface GetHintDataAccessInterface {

    /**
     * Returns the full list of progressive hints for an object.
     * @param objectID the ID of the object the user clicked on to get hint
     * @return the list of progressive hints for the object with objectID
     */
    List<String> getAllHintsForObject(String objectID);

    /**
     * Returns the absolute total number of hints written for this object (as saved in the hints database).
     * @param objectID the ID of the object the user clicked on to get hint
     * @return the total number of hints written for this object
     * (the length of the list containing the hints for this object)
     */
    int getMaxHintsAvailable(String objectID);

    /**
     * Checks if an object can access a hint.
     * @param objectID the ID of the object
     * @return true if the object has a hint related to it, false otherwise
     */
    Boolean existByObjectID(String objectID);
}
