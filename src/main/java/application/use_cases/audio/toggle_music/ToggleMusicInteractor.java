package application.use_cases.audio.toggle_music;

import domain.entities.audio.AudioSettings;

/**
 * Interactor responsible for toggling the application's music state.
 *
 * <p>This class implements the {@link ToggleMusicInputBoundary} and manages
 * the current music state. After toggling the state, it passes the updated
 * state to the output boundary.</p>
 */
public class ToggleMusicInteractor implements ToggleMusicInputBoundary {

    private final ToggleMusicOutputBoundary presenter;
    private AudioSettings audioSettings;

    /**
     * Creates a new toggle music interactor.
     *
     * @param presenter output boundary used to present the updated music state
     */
    public ToggleMusicInteractor(ToggleMusicOutputBoundary presenter, AudioSettings audioSettings) {
        this.presenter = presenter;
        this.audioSettings = audioSettings;
    }

    /**
     * Toggles the music between on and off and presents the updated state.
     */
    @Override
    public void toggleMusic() {
        audioSettings.toggleSfx();
        presenter.presentMusicState(new ToggleMusicOutputData(audioSettings.isSfxOn()));
    }
}
