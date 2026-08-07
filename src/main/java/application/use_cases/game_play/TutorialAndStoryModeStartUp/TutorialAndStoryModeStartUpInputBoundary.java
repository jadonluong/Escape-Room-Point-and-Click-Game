package application.use_cases.game_play.TutorialAndStoryModeStartUp;

/**
 * Input boundary for starting the tutorial or story game mode.
 */
public interface TutorialAndStoryModeStartUpInputBoundary {

    /**
     * Executes the startup process for the selected game mode.
     *
     * @param inputData the input data specifying the game mode to start
     */
    void execute(TutorialAndStoryModeStartUpInputData inputData);
}
