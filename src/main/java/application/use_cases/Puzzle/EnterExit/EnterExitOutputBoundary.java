package application.use_cases.Puzzle.EnterExit;

public interface EnterExitOutputBoundary {
    void prepareEnterView(EnterExitOutputData outputData);
    void prepareExitView(); // Return to ZoomInView.
    void prepareFailureView(String errorMessage); // If player doesn't have the cipherKey Item!
}
