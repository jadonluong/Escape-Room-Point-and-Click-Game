package domain.entities.User;

import java.util.HashMap;

/**
 * The interface with methods related to the hints the user has watched.
 */
public interface UserHints {

    /**
     * Saves the hint the user has clicked on.
     * @param objectID the ID of the object the hint is related to
     */
    void saveHint(String objectID, int maxHintsAvailable);

    /**
     * Returns the hints the user has watched, the keys are the object IDs the hints is related to
     * and the values are the request count.
     * @return the map with objectID as keys and the number of times the user has watched
     * the hint (starting at 0) as values
     */
    HashMap<String, Integer> getHintsWatched();

    /**
     * Restores the user with the loadedHints.
     * @param loadedHints the hints the user has already watched and stored in the database
     */
    void setHintProgress(HashMap<String, Integer> loadedHints);
}
