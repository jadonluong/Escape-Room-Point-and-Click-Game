package domain.entities.Hint;

import java.util.UUID;

/**
 * Factory for creating hints.
 */
public interface HintFactory {

    /**
     * Creates and returns the hint object with the given ID and message.
     * @param ID the hint ID
     * @param message the hint message to be displayed
     * @return the Hint object
     */
    Hint createHint(UUID ID, String message);
}
