package view.common;

import interface_adapter.Audio.AudioViewModel;
import interface_adapter.Audio.ToggleMusicController;
import interface_adapter.Audio.ToggleSfxController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AudioControlView extends HBox implements PropertyChangeListener {

    private final AudioViewModel audioViewModel;
    private final Button sfxButton = new Button();
    private final Button musicButton = new Button();

    public AudioControlView(ToggleSfxController sfxController,
                            ToggleMusicController musicController,
                            AudioViewModel audioViewModel) {
        this.audioViewModel = audioViewModel;

        setSpacing(12);
        setAlignment(Pos.CENTER);

        sfxButton.setStyle("-fx-font-size: 20px; -fx-cursor: hand;");
        musicButton.setStyle("-fx-font-size: 20px; -fx-cursor: hand;");

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
        // TODO: swap these text placeholders for the real speaker/music-note
        // icon images once assets exist — same setGraphic(...) pattern used
        // for the main menu's image buttons.
        sfxButton.setText(audioViewModel.getState().isSfxOn() ? "\uD83D\uDD0A" : "\uD83D\uDD07");
        musicButton.setText(audioViewModel.getState().isMusicOn() ? "\uD83C\uDFB5" : "\uD83D\uDD87");
    }
}
