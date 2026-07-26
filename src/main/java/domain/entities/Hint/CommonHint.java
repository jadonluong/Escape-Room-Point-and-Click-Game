package domain.entities.Hint;

import domain.entities.Room.Position;

import java.util.List;

/**
 * The CommonHint class that implements the Hint interface.
 */
public class CommonHint implements Hint{
    private final String objectID;
    private final List<String> hintMessages;
    private final String imagePath;
    private Position position;

    public CommonHint(String ID, String imagePath, List<String> hintMessages, Position position) {
        this.objectID = ID;
        this.imagePath = imagePath;
        this.hintMessages = hintMessages;
        this.position = position;
    }

    @Override
    public String getObjectID() {
        return this.objectID;
    }

    @Override
    public String getHintMessageForRequestCount(int requestCount) {
        return this.hintMessages.get(requestCount);
    }

    @Override
    public int getHintMessageCount() {
        return this.hintMessages.size();
    }

    @Override
    public Position getHintObjectPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }
}
