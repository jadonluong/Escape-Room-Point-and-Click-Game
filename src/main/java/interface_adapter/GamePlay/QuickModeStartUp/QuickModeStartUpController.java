package interface_adapter.GamePlay.QuickModeStartUp;

import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInputBoundary;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInputData;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInputBoundary;

public class QuickModeStartUpController {

    private final QuickModeStartUpInputBoundary Interactor;

    public QuickModeStartUpController(QuickModeStartUpInputBoundary interactor) {
        this.Interactor = interactor;
    }
    public void execute(String roomId) {
        QuickModeStartUpInputData data = new QuickModeStartUpInputData(roomId);
        Interactor.execute(data);
    }
}
