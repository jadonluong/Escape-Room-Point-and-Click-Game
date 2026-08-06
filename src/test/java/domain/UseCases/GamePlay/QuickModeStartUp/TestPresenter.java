package domain.UseCases.GamePlay.QuickModeStartUp;

import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputBoundary;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpOutputData;

class TestPresenter implements QuickModeStartUpOutputBoundary {
    private QuickModeStartUpOutputData successData;
    private String errorMessage;

    @Override
    public void prepareGameStartView(QuickModeStartUpOutputData outputData) {
        this.successData = outputData;
    }

    @Override
    public void prepareFailView(String error) {
        this.errorMessage = error;
    }

    public QuickModeStartUpOutputData getSuccessData() {
        return successData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}