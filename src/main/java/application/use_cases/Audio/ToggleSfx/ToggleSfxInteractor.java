package application.use_cases.Audio.ToggleSfx;

/**
 * Interactor responsible for toggling the sound effects state and
 * presenting the updated state to the output boundary.
 */
public class ToggleSfxInteractor implements ToggleSfxInputBoundary {

    private final ToggleSfxOutputBoundary presenter;
    private boolean sfxOn = true;

    /**
     * Creates a {@code ToggleSfxInteractor} with the specified presenter.
     *
     * @param presenter the output boundary used to present the updated
     *                  sound effects state
     */
    public ToggleSfxInteractor(ToggleSfxOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Toggles the sound effects state and presents the updated state.
     */
    @Override
    public void toggleSfx() {
        sfxOn = !sfxOn;
        presenter.presentSfxState(new ToggleSfxOutputData(sfxOn));
    }
}
