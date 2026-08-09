package application.use_cases.audio.toggle_music;

/**
 * Defines the input boundary for toggling the application's music.
 *
 * <p>Implementations of this interface handle the request to enable or
 * disable background music.</p>
 */
public interface ToggleMusicInputBoundary {

    /**
     * Toggles the current music state between enabled and disabled.
     */
    void toggleMusic();
}
