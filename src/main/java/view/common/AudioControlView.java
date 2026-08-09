package view.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import interface_adapter.audio.AudioViewModel;
import interface_adapter.audio.ToggleMusicController;
import interface_adapter.audio.ToggleSfxController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A view containing buttons for toggling sound effects and background music.
 * The displayed icons automatically update in response to changes in the
 * audio state.
 */
public class AudioControlView extends HBox implements PropertyChangeListener {

    private static final double ICON_SIZE = 200;
    private static final int SPACING = 25;

    private final AudioViewModel audioViewModel;
    private final Button sfxButton = new Button();
    private final Button musicButton = new Button();

    private final Image sfxOnImage = loadImage("/images/ui/buttons/ClickButtonOn.png");
    private final Image sfxOffImage = loadImage("/images/ui/buttons/ClickButtonOff.png");
    private final Image musicOnImage = loadImage("/images/ui/buttons/MusicButtonOn.png");
    private final Image musicOffImage = loadImage("/images/ui/buttons/MusicButtonOff.png");

    /**
     * Constructs an audio control view.
     *
     * @param sfxController controller used to toggle sound effects
     * @param musicController controller used to toggle background music
     * @param audioViewModel the view model containing the current audio state
     */
    public AudioControlView(ToggleSfxController sfxController,
                            ToggleMusicController musicController,
                            AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;

        setSpacing(SPACING);
        setAlignment(Pos.CENTER);

        sfxButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        musicButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        sfxButton.setCursor(Cursor.HAND);
        musicButton.setCursor(Cursor.HAND);

        sfxButton.setOnAction(evt -> sfxController.toggleSfx());
        musicButton.setOnAction(evt -> musicController.toggleMusic());

        getChildren().addAll(sfxButton, musicButton);

        audioViewModel.addPropertyChangeListener(this);
        render();
    }

    /**
     * Updates the displayed icons whenever the audio state changes.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        render();
    }

    /**
     * Refreshes the button icons to reflect the current audio settings.
     */
    private void render() {
        if (audioViewModel.getState().isSfxOn()) {
            sfxButton.setGraphic(makeIcon(sfxOnImage));
        }
        else {
            sfxButton.setGraphic(makeIcon(sfxOffImage));
        }

        if (audioViewModel.getState().isMusicOn()) {
            musicButton.setGraphic(makeIcon(musicOnImage));
        }
        else {
            musicButton.setGraphic(makeIcon(musicOffImage));
        }
    }

    /**
     * Creates an image view for a button icon.
     *
     * @param image the image to display
     * @return an image view with the configured icon size
     */
    private ImageView makeIcon(Image image) {
        ImageView icon = new ImageView(image);
        icon.setFitWidth(ICON_SIZE);
        icon.setFitHeight(ICON_SIZE);
        return icon;
    }

    /**
     * Loads an image from the application's resources.
     *
     * @param resourcePath the path to the image resource
     * @return the loaded image
     * @throws IllegalArgumentException if the resource cannot be found
     */
    private Image loadImage(String resourcePath) {
        InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }
}
