package interface_adapter.audio;

/**
 * Stores the current state of the game's audio settings.
 *
 * <p>The state tracks whether background music and sound effects are enabled.</p>
 */
public class AudioState {
    private boolean musicOn = true;
    private boolean sfxOn = true;

    /**
     * Returns whether background music is enabled.
     *
     * @return {@code true} if music is enabled; {@code false} otherwise
     */
    public boolean isMusicOn() {
        return musicOn;
    }

    /**
     * Sets whether background music is enabled.
     *
     * @param musicOn {@code true} to enable music; {@code false} to disable it
     */
    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    /**
     * Returns whether sound effects are enabled.
     *
     * @return {@code true} if sound effects are enabled; {@code false} otherwise
     */
    public boolean isSfxOn() {
        return sfxOn;
    }

    /**
     * Sets whether sound effects are enabled.
     *
     * @param sfxOn {@code true} to enable sound effects; {@code false} to disable them
     */
    public void setSfxOn(boolean sfxOn) {
        this.sfxOn = sfxOn;
    }
}
