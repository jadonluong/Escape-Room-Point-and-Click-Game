package application.use_cases.game_play.tutorial_and_story_mode_start_up;

/**
 * Input data specifying the game mode to start.
 */
public class TutorialAndStoryModeStartUpInputData {
    private final String mode;

    /**
     * Creates startup input data for the specified game mode.
     *
     * @param mode the game mode to start
     */
    public TutorialAndStoryModeStartUpInputData(String mode) {
        this.mode = mode;
    }

    /**
     * Returns the game mode to start.
     *
     * @return the selected game mode
     */
    public String getMode() {
        return mode;
    }
}
