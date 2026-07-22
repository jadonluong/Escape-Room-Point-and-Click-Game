package application.use_cases.Interactable.Interact;

public class InteractOutputData {
    private String successMessage;

    public InteractOutputData(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}