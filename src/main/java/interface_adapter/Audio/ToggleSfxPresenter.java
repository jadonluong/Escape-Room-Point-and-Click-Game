package interface_adapter.Audio;

import application.use_cases.Audio.ToggleSfx.ToggleSfxOutputBoundary;
import application.use_cases.Audio.ToggleSfx.ToggleSfxOutputData;

/**
 * Presenter for the toggle sound effects use case.
 *
 * <p>Updates the audio view model with the current sound effects state and
 * notifies listeners when the state changes.</p>
 */
public class ToggleSfxPresenter implements ToggleSfxOutputBoundary {

    /** View model containing the current audio settings. */
    private final AudioViewModel audioViewModel;

    /**
     * Creates a toggle sound effects presenter.
     *
     * @param audioViewModel view model containing the audio state
     */
    public ToggleSfxPresenter(AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;
    }

    /**
     * Presents the updated sound effects state to the audio view model.
     *
     * @param outputData output data containing the updated sound effects state
     */
    @Override
    public void presentSfxState(ToggleSfxOutputData outputData) {
        audioViewModel.getState().setSfxOn(outputData.isSfxOn());
        audioViewModel.firePropertyChanged("sfx");
    }
}
