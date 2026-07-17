package domain.entities.Hint;

import java.util.UUID;

/**
 * The CommonHint class that implements the Hint interface.
 */
public class CommonHint implements Hint{
    private UUID hintID;
    private String hintMessage;

    public CommonHint(UUID ID, String hintMessage) {
        this.hintID = ID;
        this.hintMessage = hintMessage;
    }

    @Override
    public String getHintID() {
        return this.hintID.toString();
    }

    @Override
    public String getHintMessage() {
        return this.hintMessage;
    }
}
