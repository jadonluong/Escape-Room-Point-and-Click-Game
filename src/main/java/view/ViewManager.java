package view;

import interface_adapter.ViewManagerModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.common.ModalOverlay;
import view.common.SoundPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ViewManager implements PropertyChangeListener {
    private static final double INITIAL_WIDTH = 1280;
    private static final double INITIAL_HEIGHT = 720;

    private final Stage stage;
    private final ViewManagerModel viewManagerModel;
    private final SoundPlayer soundPlayer;
    private final Supplier<Boolean> sfxEnabledSupplier;

    private final Map<String, Parent> views = new HashMap<>();
    private Parent currentView; // In case someone wants to get this value (there is a getter!)
    private String currentViewName; // ^^

    private final Map<String, ModalOverlay> overlays = new HashMap<>();
    private final Set<String> topLayerOverlays = new HashSet<>(); // Overlays that sit on top when visible.
    private final Set<String> visibleOverlays = new HashSet<>();

    private Scene scene;

    public ViewManager(Stage stage, ViewManagerModel viewManagerModel,
                       SoundPlayer soundPlayer, Supplier<Boolean> sfxEnabledSupplier) {
        this.stage = stage;
        this.viewManagerModel = viewManagerModel;
        this.soundPlayer = soundPlayer;
        this.sfxEnabledSupplier = sfxEnabledSupplier;

        this.viewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Registers a view under a name so ViewManagerModel state changes can find it later.
     * Call this for every top-level screen during app startup, before triggering the first navigation.
     */
    public void registerView(String viewName, Parent view) {
        views.put(viewName, view);
    }

    public void registerOverlay(String viewName, ModalOverlay overlay, boolean topLayer) {
        overlays.put(viewName, overlay);
        if (topLayer) {
            topLayerOverlays.add(viewName);
        }
        overlay.setVisible(false);
        overlay.setManaged(false);
    }

    public void registerOverlay(String viewName, ModalOverlay overlay) {
        registerOverlay(viewName, overlay, false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String viewName = (String) evt.getNewValue();

        Parent view = views.get(viewName);
        if (view == null) {
            System.err.println("No view registered for name: " + viewName);
            return;
        }

        hideAllNonTopLayerOverlays();
        this.currentView = view;
        this.currentViewName = viewName;

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

    public void showOverlay(String overlayName) {
        ModalOverlay overlay = overlays.get(overlayName);
        if (overlay == null) {
            return;
        }

        if (visibleOverlays.contains(overlayName)) {
            return;
        }

        if (scene == null) {
            return;
        }

        Parent root = scene.getRoot();
        if (!(root instanceof StackPane)) {
            return;
        }
        ((StackPane) root).getChildren().add(overlay);

        overlay.setVisible(true);
        overlay.setManaged(true);
        visibleOverlays.add(overlayName);

        if (topLayerOverlays.contains(overlayName)) {
            overlay.toFront();
        } else {
            for (String topName : topLayerOverlays) {
                ModalOverlay topOverlay = overlays.get(topName);
                if (visibleOverlays.contains(topName)) {
                    topOverlay.toFront();
                }
            }
        }
    }

    public void hideOverlay(String overlayName) {
        ModalOverlay overlay = overlays.get(overlayName);
        if (overlay == null) {
            return;
        }

        if (!visibleOverlays.contains(overlayName)) { // Already hidden...
            return;
        }

        if (scene == null) {
            return;
        }

        Parent root = scene.getRoot();
        if (!(root instanceof StackPane)) {
            return;
        }
        ((StackPane) root).getChildren().remove(overlay);

        overlay.setVisible(false);
        overlay.setManaged(false);
        visibleOverlays.remove(overlayName);
    }

    private void hideAllNonTopLayerOverlays() {
        Set<String> visibleCopy = new HashSet<>(visibleOverlays);
        for (String overlayName : visibleCopy) {
            if (topLayerOverlays.contains(overlayName)) {
                continue;
            }
            hideOverlay(overlayName); // Removes the overlay from visibleOverlays, so iterate over a copy!
        }
    }

    public Parent getCurrentView() {
        return currentView;
    }

    public String getCurrentViewName() {
        return currentViewName;
    }
}