package view;

import interface_adapter.ViewManagerModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.common.SoundPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ViewManager implements PropertyChangeListener {
    private static final double INITIAL_WIDTH = 1280;
    private static final double INITIAL_HEIGHT = 720;

    private final Stage stage;
    private final SoundPlayer soundPlayer;
    private final Supplier<Boolean> sfxEnabledSupplier;
    private final Map<String, Parent> views = new HashMap<>();
    private Scene scene;

    public ViewManager(Stage stage, ViewManagerModel viewManagerModel,
                       SoundPlayer soundPlayer, Supplier<Boolean> sfxEnabledSupplier) {
        this.stage = stage;
        this.soundPlayer = soundPlayer;
        this.sfxEnabledSupplier = sfxEnabledSupplier;
        viewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Registers a view under a name so ViewManagerModel state changes can find it later.
     * Call this for every top-level screen during app startup, before triggering the first navigation.
     */
    public void registerView(String viewName, Parent view) {
        views.put(viewName, view);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String viewName = (String) evt.getNewValue();
        Parent view = views.get(viewName);
        if (view == null) {
            System.err.println("No view registered for name: " + viewName);
            return;
        }

        if (scene == null) {
            scene = new Scene(view, INITIAL_WIDTH, INITIAL_HEIGHT);
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (sfxEnabledSupplier.get()) {
                    soundPlayer.playClick();
                }
            });
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.setTitle("Escapists");
            stage.show();
        } else {
            scene.setRoot(view);
        }
    }

    public void show(Parent view) {
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(view, INITIAL_WIDTH, INITIAL_HEIGHT);
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (sfxEnabledSupplier.get()) {
                    soundPlayer.playClick();
                }
            });
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
        } else {
            scene.setRoot(view);
        }
        stage.show();
    }

}