package application.use_cases.Audio.ToggleMusic;

public class ToggleMusicOutputData {
    private final boolean musicOn;

    public ToggleMusicOutputData(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public boolean isMusicOn() {
        return musicOn;
    }
}
