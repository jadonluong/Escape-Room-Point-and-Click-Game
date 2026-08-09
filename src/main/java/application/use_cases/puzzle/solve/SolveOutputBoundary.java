package application.use_cases.puzzle.solve;

public interface SolveOutputBoundary {
    void prepareSuccessView(SolveOutputData outputData);
    void prepareFailureView(String errorMessage);
}
