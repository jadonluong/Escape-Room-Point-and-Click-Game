package view.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.User.LoggedIn.LoggedInViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import view.ViewManager;
import view.common.AbstractModalOverlay;
import view.common.AudioControlView;

/**
 * Displays the in-game menu overlay.
 *
 * <p>The menu allows the player to save their game, save and quit, quit
 * without saving, and control audio settings. Save-related options are
 * displayed only when a user is logged in.</p>
 */
public class GameMenuView extends AbstractModalOverlay implements PropertyChangeListener {

    private static final int SPACING_V = 16;
    private static final int PADDING = 24;
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private static final int PREF_WIDTH = 220;
    private static final int PREF_HEIGHT = 40;

    private static final String FONT_WEIGHT = "-fx-font-weight: bold; ";
    private static final String BG_RADIUS = "-fx-background-radius: 8; ";
    private static final String CURSOR_SETTINGS = "-fx-cursor: hand; ";
    private static final String INGAME_MENU = "in-game menu";

    private final LoggedInViewModel loggedInViewModel;
    private final AudioControlView audioControlView;
    private final Runnable onSave;
    private final Runnable onSaveAndQuit;
    private final Runnable onQuit;
    private final ViewManager viewManager;

    private VBox saveArea;

    /**
     * Creates the in-game menu overlay.
     *
     * @param viewManager the manager responsible for displaying and hiding overlays
     * @param loggedInViewModel the view model containing the user's login state
     * @param audioControlView the controls for sound effects and background music
     * @param onSave the action to perform when the game is saved
     * @param onSaveAndQuit the action to perform when the game is saved and exited
     * @param onQuit the action to perform when the game is exited without saving
     */
    public GameMenuView(
            ViewManager viewManager,
            LoggedInViewModel loggedInViewModel,
            AudioControlView audioControlView,
            Runnable onSave,
            Runnable onSaveAndQuit,
            Runnable onQuit) {

        super(() -> viewManager.hideOverlay(INGAME_MENU));
        this.viewManager = viewManager;
        this.loggedInViewModel = loggedInViewModel;
        this.audioControlView = audioControlView;
        this.onSave = onSave;
        this.onSaveAndQuit = onSaveAndQuit;
        this.onQuit = onQuit;

        loggedInViewModel.addPropertyChangeListener(this);
        initialize();
    }

    /**
     * Builds the in-game menu modal box.
     *
     * @return the VBox containing the menu controls
     */
    @Override
    protected VBox buildModalBox() {
        Label title = new Label("PAUSED");
        title.setStyle(
                "-fx-font-size: 28px; "
                        + FONT_WEIGHT
                        + "-fx-text-fill: white;");

        VBox box = new VBox(SPACING_V, title);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(PADDING));
        box.setMaxWidth(MAX_WIDTH);
        box.setMaxHeight(MAX_HEIGHT);
        box.setStyle(
                "-fx-background-color: #1997d4; "
                        + "-fx-background-radius: 16;");

        saveArea = new VBox(SPACING_V);
        saveArea.setAlignment(Pos.CENTER);

        updateSaveButton();

        Button quitButton = new Button("QUIT");
        quitButton.setPrefWidth(PREF_WIDTH);
        quitButton.setPrefHeight(PREF_HEIGHT);
        quitButton.setStyle(
                "-fx-font-size: 18px; "
                        + FONT_WEIGHT
                        + BG_RADIUS
                        + CURSOR_SETTINGS);
        quitButton.setOnAction(evt -> onQuit.run());

        Label quitNote = new Label("* will not save!");
        quitNote.setStyle(
                "-fx-text-fill: white; "
                        + "-fx-font-size: 11px;");

        box.getChildren().addAll(
                saveArea,
                quitButton,
                quitNote,
                audioControlView);

        return box;
    }

    /**
     * Displays the in-game menu overlay.
     */
    public void show() {
        viewManager.showOverlay(INGAME_MENU);
    }

    /**
     * Hides the in-game menu overlay.
     */
    public void hide() {
        viewManager.hideOverlay(INGAME_MENU);
    }

    /**
     * Updates the save controls when the user's login state changes.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateSaveButton();
    }

    /**
     * Updates the save buttons based on whether a user is logged in.
     */
    private void updateSaveButton() {
        saveArea.getChildren().clear();

        if (loggedInViewModel.getState().isLoggedIn()) {
            Button saveButton = new Button("SAVE");
            saveButton.setPrefWidth(PREF_WIDTH);
            saveButton.setPrefHeight(PREF_HEIGHT);
            saveButton.setStyle(
                    "-fx-font-size: 14px; "
                            + FONT_WEIGHT
                            + BG_RADIUS
                            + CURSOR_SETTINGS);
            saveButton.setOnAction(evt -> onSave.run());

            Label saveNote = new Label("* game does not auto save!");
            saveNote.setStyle(
                    "-fx-text-fill: white; "
                            + "-fx-font-size: 11px;");

            Button saveAndQuitButton = new Button("SAVE & QUIT");
            saveAndQuitButton.setPrefWidth(PREF_WIDTH);
            saveAndQuitButton.setPrefHeight(PREF_HEIGHT);
            saveAndQuitButton.setStyle(
                    "-fx-font-size: 14px; "
                            + FONT_WEIGHT
                            + BG_RADIUS
                            + CURSOR_SETTINGS);
            saveAndQuitButton.setOnAction(
                    evt -> onSaveAndQuit.run());

            saveArea.getChildren().addAll(
                    saveButton,
                    saveNote,
                    saveAndQuitButton);
        }
    }
}
