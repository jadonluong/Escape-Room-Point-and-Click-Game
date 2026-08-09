package application.use_cases.audio.toggle_sfx;

/**
 * Output data containing the current state of the sound effects.
 */
public class ToggleSfxOutputData {
    private final boolean sfxOn;

    /**
     * Creates output data representing the current sound effects state.
     *
     * @param sfxOn {@code true} if sound effects are currently enabled;
     *              {@code false} otherwise
     */
    public ToggleSfxOutputData(boolean sfxOn) {
        this.sfxOn = sfxOn;
    }

    /**
     * Returns whether sound effects are currently enabled.
     *
     * @return {@code true} if sound effects are enabled; {@code false} otherwise
     */
    public boolean isSfxOn() {
        return sfxOn;
    }
}
