package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

public interface QuickModeStartUpOutputBoundary {
    void prepareGameStartView(QuickModeStartUpOutputData outputData);
    void prepareFailView(String error);
}
