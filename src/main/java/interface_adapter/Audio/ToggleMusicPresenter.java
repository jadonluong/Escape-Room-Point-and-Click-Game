package interface_adapter.Audio;

import application.use_cases.Audio.ToggleMusic.ToggleMusicOutputBoundary;
import application.use_cases.Audio.ToggleMusic.ToggleMusicOutputData;

public class ToggleMusicPresenter implements ToggleMusicOutputBoundary {
    private final AudioViewModel audioViewModel;

    public ToggleMusicPresenter(AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;
    }

    @Override
    public void presentMusicState(ToggleMusicOutputData outputData) {
        audioViewModel.getState().setMusicOn(outputData.isMusicOn());
        audioViewModel.firePropertyChanged("music");
    }
}
