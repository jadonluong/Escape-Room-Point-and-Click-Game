package interface_adapter.game_play.tutorial_and_story_mode_start_up;

import application.use_cases.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpInputBoundary;
import application.use_cases.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpInputData;

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
        final TutorialAndStoryModeStartUpInputData inputData = new TutorialAndStoryModeStartUpInputData(mode);
        selectModeInteractor.execute(inputData);
    }
}
