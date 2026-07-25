package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

public interface TutorialAndStoryModeStartUpOutputBoundary {
    void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData tutorialAndStoryModeStartUpOutPutData);
    void prepareFailView(String message);
}
