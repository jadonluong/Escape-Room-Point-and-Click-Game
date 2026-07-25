package interface_adapter.GamePlay.SelectMode;

import application.use_cases.GamePlay.SelectMode.Navigation.ModeNavigation;
import application.use_cases.GamePlay.SelectMode.SelectModeInputBoundary;
import application.use_cases.GamePlay.SelectMode.SelectModeInputData;

public class SelectModeController {
    private final SelectModeInputBoundary selectModeInteractor;

    public SelectModeController(SelectModeInputBoundary selectModeInteractor) {
        this.selectModeInteractor = selectModeInteractor;
    }

    /**
     * Triggered by the UI View layer when a player attempts to select a room.
     * @param mode The unique identifier of the mode being selected.
     */
    public void execute(ModeNavigation mode) {
        SelectModeInputData inputData = new SelectModeInputData(mode);
        selectModeInteractor.execute(inputData);
    }
}
