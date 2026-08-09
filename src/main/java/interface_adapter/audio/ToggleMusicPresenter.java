package interface_adapter.audio;

import application.use_cases.audio.toggle_music.ToggleMusicOutputBoundary;
import application.use_cases.audio.toggle_music.ToggleMusicOutputData;

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
