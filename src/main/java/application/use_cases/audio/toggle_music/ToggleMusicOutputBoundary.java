package application.use_cases.audio.toggle_music;

/**
 * Defines the output boundary for the toggle music use case.
 *
 * <p>Implementations of this interface handle the presentation of the
 * updated music state.</p>
 */
public interface ToggleMusicOutputBoundary {

    /**
     * Presents the updated music state.
     *
     * @param outputData data containing the current music state
     */
    void presentMusicState(ToggleMusicOutputData outputData);
}
