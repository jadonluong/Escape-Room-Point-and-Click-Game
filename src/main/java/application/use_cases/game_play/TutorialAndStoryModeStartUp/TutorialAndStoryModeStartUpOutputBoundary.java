package application.use_cases.game_play.TutorialAndStoryModeStartUp;

/**
 * Output boundary for presenting the result of starting tutorial or story mode.
 */
public interface TutorialAndStoryModeStartUpOutputBoundary {

    /**
     * Prepares the view for starting the selected game mode.
     *
     * @param tutorialAndStoryModeStartUpOutPutData the output data containing
     *                                              the information required to
     *                                              start the game
     */
    void prepareGameStartView(TutorialAndStoryModeStartUpOutPutData tutorialAndStoryModeStartUpOutPutData);

    /**
     * Prepares a failure view with the specified error message.
     *
     * @param message the error message describing why startup failed
     */
    void prepareFailView(String message);
}
