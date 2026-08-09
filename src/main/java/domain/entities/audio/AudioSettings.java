package domain.entities.audio;

/**
 * Represents the player's current audio preferences.
 */
public class AudioSettings {
    private boolean sfxOn;
    private boolean musicOn;

    public AudioSettings() {
        this.sfxOn = true;
        this.musicOn = true;
    }

    public boolean isSfxOn() {
        return sfxOn;
    }

    public void toggleSfx() {
        this.sfxOn = !this.sfxOn;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void toggleMusic() {
        this.musicOn = !this.musicOn;
    }
}
