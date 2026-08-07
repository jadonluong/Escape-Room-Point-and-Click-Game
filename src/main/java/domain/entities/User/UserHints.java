package domain.entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The interface with methods related to the hints the user has watched.
 */
public interface UserHints {

    /**
     * Saves the hint the user has clicked on.
     * @param objectID the ID of the object the hint is related to
     * @param maxHintsAvailable the maximum number of hints this hint object has
     */
    void saveHint(String objectID, int maxHintsAvailable);

    /**
     * Returns the hints the user has watched, the keys are the object IDs the hints is related to
     * and the values are the request count.
     * @return the map with objectID as keys and the number of times the user has watched
     *      the hint (starting at 0) as values
     */
    HashMap<String, Integer> getHintsWatched();

    /**
     * Returns the hints the user has watched in each room in quick mode.
     * @return the hints the user has watched in each room in quick mode
     */
    Map<String, HashMap<String, Integer>> getQuickModeHintsWatched();

    /**
     * Returns the hints the user has watched in story mode.
     * @return the hints the user has watched in story mode
     */
    HashMap<String, Integer> getStoryModeHintsWatched();
}
