package interface_adapter.audio;

import application.use_cases.audio.toggle_sfx.ToggleSfxOutputBoundary;
import application.use_cases.audio.toggle_sfx.ToggleSfxOutputData;

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
