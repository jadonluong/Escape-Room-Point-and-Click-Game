package interface_adapter.Audio;

import application.use_cases.Audio.ToggleSfx.ToggleSfxOutputBoundary;
import application.use_cases.Audio.ToggleSfx.ToggleSfxOutputData;

public class ToggleSfxPresenter implements ToggleSfxOutputBoundary {
    private final AudioViewModel audioViewModel;

    public ToggleSfxPresenter(AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;
    }

    @Override
    public void presentSfxState(ToggleSfxOutputData outputData) {
        audioViewModel.getState().setSfxOn(outputData.isSfxOn());
        audioViewModel.firePropertyChanged("sfx");
    }
}
