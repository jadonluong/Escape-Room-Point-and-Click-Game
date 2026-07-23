package application.use_cases.Puzzle.Solve;

public interface SolveOutputBoundary {
    void prepareSuccessView(SolveOutputData outputData);
    void prepareFailureView(String errorMessage);
}
