package application.use_cases.audio.toggle_sfx;

/**
 * Output boundary for presenting the current sound effects state.
 */
public interface ToggleSfxOutputBoundary {

    /**
     * Presents the updated sound effects state.
     *
     * @param outputData the output data containing the current sound effects state
     */
    void presentSfxState(ToggleSfxOutputData outputData);
}
