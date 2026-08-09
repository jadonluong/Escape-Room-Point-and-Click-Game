package application.use_cases.game_play.tutorial_and_story_mode_start_up;

// Fake Presenter (Output Boundary)
public class TestPresenter implements TutorialAndStoryModeStartUpOutputBoundary {
    private TutorialAndStoryModeStartUpOutPutData successData;
    private String errorMessage;

    @Override
    public void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData outputData) {
        this.successData = outputData;
    }

    @Override
    public void prepareFailView(String error) {
        this.errorMessage = error;
    }

    public TutorialAndStoryModeStartUpOutPutData getSuccessData() {
        return successData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
