package application.use_cases.Audio.ToggleSfx;

public class ToggleSfxOutputData {
    private final boolean sfxOn;

    public ToggleSfxOutputData(boolean sfxOn) {
        this.sfxOn = sfxOn;
    }

    public boolean isSfxOn() {
        return sfxOn;
    }
}
