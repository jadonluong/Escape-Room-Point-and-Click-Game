package view.common;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private final MediaPlayer mediaPlayer;

    public MusicPlayer(String musicResourcePath) {
        java.net.URL url = getClass().getResource(musicResourcePath);
        if (url == null) {
            System.err.println("Background music not found at " + musicResourcePath
                    + " — music will be silently skipped until the asset is added.");
            this.mediaPlayer = null;
            return;
        }
        Media media = new Media(url.toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void setMuted(boolean muted) {
        if (mediaPlayer != null) {
            mediaPlayer.setMute(muted);
        }
    }
}
