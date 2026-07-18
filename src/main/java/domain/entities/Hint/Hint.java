package domain.entities.Hint;

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
     * @return the hint message
     */
    String getHintMessageForRequestCount(int requestCount);
}
