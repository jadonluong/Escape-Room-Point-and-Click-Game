package domain.entities.Hint;

import java.util.List;

/**
 * The CommonHint class that implements the Hint interface.
 */
public class CommonHint implements Hint{
    private String objectID;
    private List<String> hintMessages;

    public CommonHint(String ID, List<String> hintMessages) {
        this.objectID = ID;
        this.hintMessages = hintMessages;
    }

    @Override
    public String getObjectID() {
        return this.objectID;
    }

    @Override
    public String getHintMessageForRequestCount(int requestCount) {
        if (this.hintMessages == null || hintMessages.isEmpty()) {
            return "No hints available.";
        }

        int index = Math.min(requestCount, hintMessages.size() - 1);
        return this.hintMessages.get(index);
    }
}
