package interface_adapter.game_play.quick_mode_start_up;

import application.use_cases.game_play.quick_play.quick_mode_start_up.QuickModeStartUpInputData;
import application.use_cases.game_play.quick_play.quick_mode_start_up.QuickModeStartUpInteractor;

public class QuickModeStartUpController {
    private QuickModeStartUpInteractor quickModeStartUpInteractor;

    public QuickModeStartUpController(QuickModeStartUpInteractor interactor) {
        this.quickModeStartUpInteractor = interactor;
    }

    /**
     * Executes the quick mode startup process for the specified room.
     *
     * @param roomId the ID of the room to start the game in
     */
    public void execute(String roomId) {

        final QuickModeStartUpInputData data = new QuickModeStartUpInputData(roomId);
        quickModeStartUpInteractor.execute(data);
    }
}
