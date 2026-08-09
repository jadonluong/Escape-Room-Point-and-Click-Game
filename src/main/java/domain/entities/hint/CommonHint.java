package domain.entities.hint;

import java.util.List;

/**
 * The CommonHint class that implements the Hint interface.
 */
public class CommonHint implements Hint {
    private final String objectID;
    private final List<String> hintMessages;
    private final String imagePath;

    public CommonHint(String id, String imagePath, List<String> hintMessages) {
        this.objectID = id;
        this.imagePath = imagePath;
        this.hintMessages = hintMessages;
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
    public String getImagePath() {
        return this.imagePath;
    }
}
