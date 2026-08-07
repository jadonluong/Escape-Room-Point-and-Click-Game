package application.use_cases.Audio.ToggleMusic;

/**
 * Interactor responsible for toggling the application's music state.
 *
 * <p>This class implements the {@link ToggleMusicInputBoundary} and manages
 * the current music state. After toggling the state, it passes the updated
 * state to the output boundary.</p>
 */
public class ToggleMusicInteractor implements ToggleMusicInputBoundary {

    private final ToggleMusicOutputBoundary presenter;
    private boolean musicOn = true;

    /**
     * Creates a new toggle music interactor.
     *
     * @param presenter output boundary used to present the updated music state
     */
    public ToggleMusicInteractor(ToggleMusicOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Toggles the music between on and off and presents the updated state.
     */
    @Override
    public void toggleMusic() {
        musicOn = !musicOn;
        presenter.presentMusicState(new ToggleMusicOutputData(musicOn));
    }
}
