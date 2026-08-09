package application.use_cases.audio.toggle_sfx;

import domain.entities.audio.AudioSettings;

public class ToggleSfxInteractor implements ToggleSfxInputBoundary {
    private final ToggleSfxOutputBoundary presenter;
    private final AudioSettings audioSettings;

    public ToggleSfxInteractor(ToggleSfxOutputBoundary presenter, AudioSettings audioSettings) {
        this.presenter = presenter;
        this.audioSettings = audioSettings;
    }

    @Override
    public void toggleSfx() {
        audioSettings.toggleSfx();
        presenter.presentSfxState(new ToggleSfxOutputData(audioSettings.isSfxOn()));
    }
}
