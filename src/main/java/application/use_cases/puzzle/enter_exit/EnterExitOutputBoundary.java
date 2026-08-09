package application.use_cases.puzzle.enter_exit;

public interface EnterExitOutputBoundary {
    void prepareEnterView(EnterExitOutputData outputData);
    void prepareExitView(); // Return to ZoomInView.
    void prepareFailureView(String errorMessage); // If player doesn't have the cipherKey Item!
}
