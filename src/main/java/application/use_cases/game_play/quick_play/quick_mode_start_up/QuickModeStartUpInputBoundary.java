package application.use_cases.game_play.quick_play.quick_mode_start_up;

public interface QuickModeStartUpInputBoundary {

    /**
     * Executes the quick mode startup use case with the provided input data.
     *
     * @param inputData the data required to initialize a quick mode game
     */
    void execute(QuickModeStartUpInputData inputData);
}
