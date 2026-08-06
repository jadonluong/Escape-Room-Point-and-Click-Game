package application.use_cases.game_play.TutorialAndStoryModeStartUp;

public interface TutorialAndStoryModeStartUpOutputBoundary {
    void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData tutorialAndStoryModeStartUpOutPutData);
    void prepareFailView(String message);
}
