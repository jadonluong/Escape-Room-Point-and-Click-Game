package interface_adapter.Audio;

public class AudioState {
    private boolean musicOn = true;
    private boolean sfxOn = true;

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public boolean isSfxOn() { return sfxOn; }
    public void setSfxOn(boolean sfxOn) { this.sfxOn = sfxOn; }
}
