package interface_adapter.hint;

public class GetHintState {
    private String errorMessage = "";
    private String successMessage = "";

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setSuccessMessage(String message) {
        this.successMessage = message;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
