package interface_adapter.GamePlay.QuickModeStartUp;

import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInputData;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInteractor;

public class QuickModeStartUpController {
    private QuickModeStartUpInteractor quickModeStartUpInteractor;

    public QuickModeStartUpController(QuickModeStartUpInteractor interactor) {
        this.quickModeStartUpInteractor = interactor;
    }
    public void execute(String roomId) {
        QuickModeStartUpInputData data = new QuickModeStartUpInputData(roomId);
        quickModeStartUpInteractor.execute(data);
    }
}
