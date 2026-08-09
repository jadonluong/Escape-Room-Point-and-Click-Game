package view.common;

import javafx.scene.media.AudioClip;

/**
 * Plays sound effects for user interface interactions.
 *
 * <p>This class currently manages a click sound effect and safely skips
 * playback if the specified sound resource cannot be found.</p>
 */
public class SoundPlayer {

    /** Audio clip used for click sound effects. */
    private final AudioClip clickClip;

    /**
     * Creates a sound player using the specified click sound resource.
     *
     * @param clickSoundResourcePath path to the click sound resource
     */
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

    /**
     * Plays the click sound effect if the sound resource was successfully loaded.
     */
    public void playClick() {
        if (clickClip != null) {
            clickClip.play();
        }
    }
}
