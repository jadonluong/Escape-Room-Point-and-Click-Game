package domain.entities.Hint;

import domain.entities.Room.Position;

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

    /**
     * Returns the coordinates of the hint object image position in a room.
     *
     * @return the coordinates of the hint object image position in a room as a list, the first number is the x coordinate and the second is the y coordinate
     */
    Position getHintObjectPosition();
}
