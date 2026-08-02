package view.common;

import interface_adapter.Audio.AudioViewModel;
import interface_adapter.Audio.ToggleMusicController;
import interface_adapter.Audio.ToggleSfxController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

public class AudioControlView extends HBox implements PropertyChangeListener {

    private static final double ICON_SIZE = 200;

    private final AudioViewModel audioViewModel;
    private final Button sfxButton = new Button();
    private final Button musicButton = new Button();

    private final Image sfxOnImage = loadImage("/images/ui/buttons/ClickButtonOn.png");
    private final Image sfxOffImage = loadImage("/images/ui/buttons/ClickButtonOff.png");
    private final Image musicOnImage = loadImage("/images/ui/buttons/MusicButtonOn.png");
    private final Image musicOffImage = loadImage("/images/ui/buttons/MusicButtonOff.png");

    public AudioControlView(ToggleSfxController sfxController,
                            ToggleMusicController musicController,
                            AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;

        setSpacing(25);
        setAlignment(Pos.CENTER);

        sfxButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        musicButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        sfxButton.setCursor(Cursor.HAND);
        musicButton.setCursor(Cursor.HAND);

        sfxButton.setOnAction(e -> sfxController.toggleSfx());
        musicButton.setOnAction(e -> musicController.toggleMusic());

        getChildren().addAll(sfxButton, musicButton);

        audioViewModel.addPropertyChangeListener(this);
        render();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        render();
    }

    private void render() {
        sfxButton.setGraphic(makeIcon(audioViewModel.getState().isSfxOn() ? sfxOnImage : sfxOffImage));
        musicButton.setGraphic(makeIcon(audioViewModel.getState().isMusicOn() ? musicOnImage : musicOffImage));
    }

    private ImageView makeIcon(Image image) {
        ImageView icon = new ImageView(image);
        icon.setFitWidth(ICON_SIZE);
        icon.setFitHeight(ICON_SIZE);
        return icon;
    }

    private Image loadImage(String resourcePath) {
        InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }
}