package view.common;

import javafx.scene.media.AudioClip;

public class SoundPlayer {

    private final AudioClip clickClip;

    public SoundPlayer(String clickSoundResourcePath) {
        java.net.URL url = getClass().getResource(clickSoundResourcePath);
        if (url == null) {
            System.err.println("Click sound not found at " + clickSoundResourcePath
                    + " — sound will be silently skipped until the asset is added.");
            this.clickClip = null;
        } else {
            this.clickClip = new AudioClip(url.toString());
        }
    }

    public void playClick() {
        if (clickClip != null) {
            clickClip.play();
        }
    }
}