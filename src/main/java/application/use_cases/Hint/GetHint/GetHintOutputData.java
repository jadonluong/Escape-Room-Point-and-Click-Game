package application.use_cases.Hint.GetHint;

/**
 * The output data for the Get Hint use case.
 */
public class GetHintOutputData {
    private String message;

    public GetHintOutputData(String hintMessage) {
        this.message = hintMessage;
    }

    public String getMessage() {
        return this.message;
    }
}
