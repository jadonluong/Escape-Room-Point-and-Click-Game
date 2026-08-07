package application.use_cases.Hint.GetHint;

/**
 * Output data for the Get Hint use case.
 */
public class GetHintOutputData {
    private String message;

    /**
     * Creates output data containing the hint message.
     *
     * @param hintMessage the hint message to present to the user
     */
    public GetHintOutputData(String hintMessage) {
        this.message = hintMessage;
    }

    /**
     * Returns the hint message.
     *
     * @return the hint message
     */
    public String getMessage() {
        return this.message;
    }
}
