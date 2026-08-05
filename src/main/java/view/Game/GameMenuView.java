package view.Game;

import interface_adapter.Audio.AudioViewModel;
import interface_adapter.Audio.ToggleMusicController;
import interface_adapter.Audio.ToggleSfxController;
import interface_adapter.User.LoggedIn.LoggedInViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import view.ViewManager;
import view.common.AudioControlView;
import view.common.ModalOverlay;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameMenuView extends ModalOverlay implements PropertyChangeListener {

    private final LoggedInViewModel loggedInViewModel;
    private final AudioControlView audioControlView;
    private final Runnable onSave;
    private final Runnable onSaveAndQuit;
    private final Runnable onQuit;
    private final ViewManager viewManager;

    private VBox saveArea;

    public GameMenuView(ViewManager viewManager, LoggedInViewModel loggedInViewModel,
                        ToggleSfxController sfxController,
                        ToggleMusicController musicController,
                        AudioViewModel audioViewModel,
                        Runnable onSave,
                        Runnable onSaveAndQuit,
                        Runnable onQuit) {

        super(() -> viewManager.hideOverlay("in-game menu"));
        this.viewManager = viewManager;
        this.loggedInViewModel = loggedInViewModel;
        this.audioControlView = new AudioControlView(sfxController, musicController, audioViewModel);
        this.onSave = onSave;
        this.onSaveAndQuit = onSaveAndQuit;
        this.onQuit = onQuit;

        loggedInViewModel.addPropertyChangeListener(this);
        initialize(); // safe now — all fields above are set first, see earlier ModalOverlay fix
    }

    @Override
    protected VBox buildModalBox() {
        Label title = new Label("PAUSED");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox box = new VBox(16, title);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(32));
        box.setMaxWidth(450);
        box.setStyle("-fx-background-color: #1997d4; -fx-background-radius: 16;");

        // Guests never see these — not disabled, not present in the layout at all.
            saveArea = new VBox(16);
            saveArea.setAlignment(Pos.CENTER);

            updateSaveButton();

//        if (loggedInViewModel.getState().isLoggedIn()) {
//            Button saveButton = new Button("SAVE");
//            saveButton.setOnAction(e -> onSave.run());
//
//            Label saveNote = new Label("* game does not auto save!");
//            saveNote.setStyle("-fx-text-fill: white; -fx-font-size: 11px;");
//
//            Button saveAndQuitButton = new Button("SAVE & QUIT");
//            saveAndQuitButton.setOnAction(e -> onSaveAndQuit.run());
//
//            box.getChildren().addAll(saveButton, saveNote, saveAndQuitButton);
//        }

        Button quitButton = new Button("QUIT");
        quitButton.setOnAction(e -> onQuit.run());
        Label quitNote = new Label("* will not save!");
        quitNote.setStyle("-fx-text-fill: white; -fx-font-size: 11px;");

        box.getChildren().addAll(saveArea, quitButton, quitNote, audioControlView);

        return box;
    }

    public void show() {
        viewManager.showOverlay("in-game menu");
    }

    public void hide() {
        viewManager.hideOverlay("in-game menu");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateSaveButton();
    }

    private void updateSaveButton() {

        saveArea.getChildren().clear();

        if (loggedInViewModel.getState().isLoggedIn()) {
            System.out.println("login");
            Button saveButton = new Button("SAVE");
            saveButton.setOnAction(e -> onSave.run());


            Label saveNote = new Label("* game does not auto save!");
            saveNote.setStyle(
                    "-fx-text-fill: white; -fx-font-size: 11px;"
            );


            Button saveAndQuitButton = new Button("SAVE & QUIT");
            saveAndQuitButton.setOnAction(
                    e -> onSaveAndQuit.run()
            );


            saveArea.getChildren().addAll(
                    saveButton,
                    saveNote,
                    saveAndQuitButton
            );
        }
    }
}