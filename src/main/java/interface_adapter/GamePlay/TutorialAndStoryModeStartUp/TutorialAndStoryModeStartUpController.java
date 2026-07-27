package interface_adapter.GamePlay.TutorialAndStoryModeStartUp;

import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpInputBoundary;
import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpInputData;

public class TutorialAndStoryModeStartUpController {
    private final TutorialAndStoryModeStartUpInputBoundary selectModeInteractor;

    public TutorialAndStoryModeStartUpController(TutorialAndStoryModeStartUpInputBoundary selectModeInteractor) {
        this.selectModeInteractor = selectModeInteractor;
    }

    /**
     * Triggered by the UI View layer when a player attempts to select a room.
     * @param mode The unique identifier of the mode being selected.
     */
    public void execute(String mode) {
        TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData(mode);
        selectModeInteractor.execute(inputData);
    }
}
