package application.use_cases.Audio.ToggleMusic;

public class ToggleMusicInteractor implements ToggleMusicInputBoundary {

    private final ToggleMusicOutputBoundary presenter;
    private boolean musicOn = true;

    public ToggleMusicInteractor(ToggleMusicOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void toggleMusic() {
        musicOn = !musicOn;
        presenter.presentMusicState(new ToggleMusicOutputData(musicOn));
    }
}
