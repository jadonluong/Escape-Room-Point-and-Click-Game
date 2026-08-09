package application.use_cases.game_play.quick_play.quick_mode_start_up;

public interface QuickModeStartUpOutputBoundary {

    /**
     * Prepares the view for starting a quick mode game.
     *
     * @param outputData the data required to initialize the quick mode game start view
     */
    void prepareGameStartView(QuickModeStartUpOutputData outputData);

    /**
     * Prepares the view to display a failure message.
     *
     * @param error the error message describing why the game start operation failed
     */
    void prepareFailView(String error);
}
