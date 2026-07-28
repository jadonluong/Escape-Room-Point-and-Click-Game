package interface_adapter.Interactable.Interact;

public class InteractState {
    private String successMessage;
    private String errorMessage;
    private String returnToView;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getReturnToView() {
        return returnToView;
    }

    public void setReturnToView(String returnToView) {
        this.returnToView = returnToView;
    }
}
