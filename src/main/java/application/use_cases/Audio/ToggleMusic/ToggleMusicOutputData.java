package application.use_cases.Audio.ToggleMusic;

/**
 * Output data containing the current state of the music.
 */
public class ToggleMusicOutputData {
    private final boolean musicOn;

    /**
     * Creates output data representing the current music state.
     *
     * @param musicOn {@code true} if music is currently enabled;
     *                {@code false} otherwise
     */
    public ToggleMusicOutputData(boolean musicOn) {
        this.musicOn = musicOn;
    }

    /**
     * Returns whether music is currently enabled.
     *
     * @return {@code true} if music is enabled; {@code false} otherwise
     */
    public boolean isMusicOn() {
        return musicOn;
    }
}
