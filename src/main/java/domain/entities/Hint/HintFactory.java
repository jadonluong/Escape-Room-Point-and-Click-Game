package domain.entities.Hint;

import java.util.List;

/**
 * Factory for creating hints.
 */
public interface HintFactory {

    /**
     * Creates and returns the hint object with the given ID and message.
     * @param ID the object ID the hint is related to
     * @param imagePath the directory path to the image of the object with the given ID
     * @param messages the hint message to be displayed
     * @return the Hint object
     */
    Hint createHint(String ID, String imagePath, List<String> messages);
}
