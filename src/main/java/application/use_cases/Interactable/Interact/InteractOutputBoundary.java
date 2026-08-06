package application.use_cases.Interactable.Interact;

public interface InteractOutputBoundary {
    void prepareSuccessView(InteractOutputData outputData);
    void prepareFailureView(String errorMessage);
    void prepareRoomView();
    void prepareMainMenuView();
}
