package domain.entities.Hint;

/**
 * The representation of a hint in the program.
 */
public interface Hint {

    /**
     * Returns the ID of the hint.
     * @return the ID of the hint
     */
    String getHintID();

    /**
     * Returns the message of the hint.
     * @return the hint message
     */
    String getHintMessage();
}
