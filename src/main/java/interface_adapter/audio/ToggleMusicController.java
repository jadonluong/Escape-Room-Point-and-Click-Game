package interface_adapter.audio;

import application.use_cases.audio.toggle_music.ToggleMusicInputBoundary;

/**
 * Controller for toggling the game's background music.
 *
 * <p>Delegates the music toggle operation to the corresponding input
 * boundary.</p>
 */
public class ToggleMusicController {

    /** Input boundary used to execute the music toggle operation. */
    private final ToggleMusicInputBoundary interactor;

    /**
     * Creates a music toggle controller.
     *
     * @param interactor input boundary responsible for toggling the music
     */
    public ToggleMusicController(ToggleMusicInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Toggles the game's background music.
     */
    public void toggleMusic() {
        interactor.toggleMusic();
    }
}
