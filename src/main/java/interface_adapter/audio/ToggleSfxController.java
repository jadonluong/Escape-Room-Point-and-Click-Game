package interface_adapter.audio;

import application.use_cases.audio.toggle_sfx.ToggleSfxInputBoundary;

/**
 * Controller for toggling the game's sound effects.
 *
 * <p>Delegates the sound effects toggle operation to the corresponding input
 * boundary.</p>
 */
public class ToggleSfxController {

    /** Input boundary used to execute the sound effects toggle operation. */
    private final ToggleSfxInputBoundary interactor;

    /**
     * Creates a sound effects toggle controller.
     *
     * @param interactor input boundary responsible for toggling sound effects
     */
    public ToggleSfxController(ToggleSfxInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Toggles the game's sound effects.
     */
    public void toggleSfx() {
        interactor.toggleSfx();
    }
}
