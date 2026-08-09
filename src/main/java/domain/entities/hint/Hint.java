package domain.entities.hint;

/**
 * The representation of a hint in the program.
 */
public interface Hint {

    /**
     * Returns the ID of the object the hint is related to.
     * @return the ID of the object the hint is related to
     */
    String getObjectID();

    /**
     * Returns the message of the hint.
     * @param requestCount the number of request the player has
     * @return the hint message
     */
    String getHintMessageForRequestCount(int requestCount);

    /**
     * Returns the directory path of the image of the object with hint.
     * @return the directory path of the image of the object with hint
     */
    String getImagePath();

    /**
     * Returns the number of hint messages this hint object has.
     * @return the number of hint messages this hint object has
     */
    int getHintMessageCount();
}
