package interface_adapter.audio;

import application.use_cases.audio.toggle_music.ToggleMusicOutputBoundary;
import application.use_cases.audio.toggle_music.ToggleMusicOutputData;

/**
 * Presenter for the toggle music use case.
 *
 * <p>Updates the audio view model with the current music state and notifies
 * listeners when the state changes.</p>
 */
public class ToggleMusicPresenter implements ToggleMusicOutputBoundary {

    /** View model containing the current audio settings. */
    private final AudioViewModel audioViewModel;

    /**
     * Creates a toggle music presenter.
     *
     * @param audioViewModel view model containing the audio state
     */
    public ToggleMusicPresenter(AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;
    }

    /**
     * Presents the updated music state to the audio view model.
     *
     * @param outputData output data containing the updated music state
     */
    @Override
    public void presentMusicState(ToggleMusicOutputData outputData) {
        audioViewModel.getState().setMusicOn(outputData.isMusicOn());
        audioViewModel.firePropertyChanged("music");
    }
}
