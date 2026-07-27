package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

public interface QuickModeStartUpOutputBoundary {
    void prepareGameStartView(QuickModeStartUpOutputData outputData);
    void prepareFailView(String error);
}
