package application.use_cases.Audio.ToggleSfx;

public class ToggleSfxInteractor implements ToggleSfxInputBoundary {

    private final ToggleSfxOutputBoundary presenter;
    private boolean sfxOn = true;

    public ToggleSfxInteractor(ToggleSfxOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void toggleSfx() {
        sfxOn = !sfxOn;
        presenter.presentSfxState(new ToggleSfxOutputData(sfxOn));
    }
}
