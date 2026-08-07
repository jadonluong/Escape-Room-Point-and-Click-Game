package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

public interface QuickModeStartUpInputBoundary {

    /**
     * Executes the quick mode startup use case with the provided input data.
     *
     * @param inputData the data required to initialize a quick mode game
     */
    void execute(QuickModeStartUpInputData inputData);
}
