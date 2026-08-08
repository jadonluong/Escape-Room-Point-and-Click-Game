package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import interface_adapter.ViewManagerInterface;
import interface_adapter.ViewManagerModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.common.AbstractModalOverlay;
import view.common.SoundPlayer;

/**
 * Manages the application's views and modal overlays.
 *
 * <p>The ViewManager listens for changes to the ViewManagerModel and updates
 * the displayed view accordingly. It also manages the registration, display,
 * and hiding of modal overlays.</p>
 */
public class ViewManager implements PropertyChangeListener, ViewManagerInterface {
    private static final double INITIAL_WIDTH = 1280;
    private static final double INITIAL_HEIGHT = 720;

    private final Stage stage;
    private final ViewManagerModel viewManagerModel;
    private final SoundPlayer soundPlayer;
    private final Supplier<Boolean> sfxEnabledSupplier;

    private final Map<String, Parent> views = new HashMap<>();

    private Parent currentView;
    private String currentViewName;

    private final Map<String, AbstractModalOverlay> overlays = new HashMap<>();

    private final Set<String> topLayerOverlays = new HashSet<>();
    private final Set<String> visibleOverlays = new HashSet<>();

    private Scene scene;

    /**
     * Creates a ViewManager with the specified stage, model, sound player,
     * and sound effects setting supplier.
     *
     * @param stage the JavaFX stage on which views are displayed
     * @param viewManagerModel the model used to communicate view changes
     * @param soundPlayer the sound player used to play interface sounds
     * @param sfxEnabledSupplier supplies whether sound effects are enabled
     */
    public ViewManager(
            Stage stage,
            ViewManagerModel viewManagerModel,
            SoundPlayer soundPlayer,
            Supplier<Boolean> sfxEnabledSupplier) {
        this.stage = stage;
        this.viewManagerModel = viewManagerModel;
        this.soundPlayer = soundPlayer;
        this.sfxEnabledSupplier = sfxEnabledSupplier;

        this.viewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Registers a view under a specified name.
     *
     * <p>The registered view can later be displayed when the corresponding
     * view name is provided by the ViewManagerModel.</p>
     *
     * @param viewName the name used to identify the view
     * @param view the JavaFX parent representing the view
     */
    public void registerView(String viewName, Parent view) {
        views.put(viewName, view);
    }

    /**
     * Registers a modal overlay under a specified name.
     *
     * <p>The overlay is initially hidden and unmanaged.</p>
     *
     * @param viewName the name used to identify the overlay
     * @param overlay the modal overlay to register
     * @param topLayer whether the overlay should remain above other overlays
     */
    public void registerOverlay(
            String viewName, AbstractModalOverlay overlay, boolean topLayer) {
        overlays.put(viewName, overlay);

        if (topLayer) {
            topLayerOverlays.add(viewName);
        }

        overlay.setVisible(false);
        overlay.setManaged(false);
    }

    /**
     * Registers a modal overlay that is not designated as a top-layer overlay.
     *
     * @param viewName the name used to identify the overlay
     * @param overlay the modal overlay to register
     */
    public void registerOverlay(String viewName, AbstractModalOverlay overlay) {
        registerOverlay(viewName, overlay, false);
    }

    /**
     * Responds to a change in the current view.
     *
     * <p>The selected view is retrieved from the registered views, all visible
     * overlays are hidden, and the selected view is displayed. The JavaFX
     * scene is created when the first view is displayed.</p>
     *
     * @param evt the property change event containing the new view name
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String viewName = (String) evt.getNewValue();

        Parent view = views.get(viewName);

        if (view == null) {
            System.err.println("No view registered for name: " + viewName);
        }
        else {
            hideAllOverlays();
            this.currentView = view;
            this.currentViewName = viewName;

            if (scene == null) {
                scene = new Scene(view, INITIAL_WIDTH, INITIAL_HEIGHT);
                scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    if (sfxEnabledSupplier.get()) {
                        soundPlayer.playClick();
                    }
                });
                stage.setScene(scene);
                stage.setResizable(true);
                stage.centerOnScreen();
                stage.setTitle("Escapists");
                stage.show();
            }
            else {
                scene.setRoot(view);
            }
        }
    }

    /**
     * Displays the specified view on the stage.
     *
     * @param view the JavaFX parent representing the view to display
     */
    public void show(Parent view) {
        Scene currentScene = stage.getScene();

        if (currentScene == null) {
            currentScene = new Scene(view, INITIAL_WIDTH, INITIAL_HEIGHT);
            currentScene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
                if (sfxEnabledSupplier.get()) {
                    soundPlayer.playClick();
                }
            });
            stage.setScene(currentScene);
            stage.setResizable(true);
            stage.centerOnScreen();
        }
        else {
            currentScene.setRoot(view);
        }

        stage.show();
    }

    /**
     * Displays a registered modal overlay.
     *
     * @param overlayName the name of the overlay to display
     */
    public void showOverlay(String overlayName) {
        if (canShowOverlay(overlayName)) {
            AbstractModalOverlay overlay = overlays.get(overlayName);
            Parent root = scene.getRoot();

            ((StackPane) root).getChildren().add(overlay);

            overlay.setVisible(true);
            overlay.setManaged(true);
            visibleOverlays.add(overlayName);

            if (topLayerOverlays.contains(overlayName)) {
                overlay.toFront();
            }
            else {
                bringTopLayerOverlaysToFront();
            }
        }
    }

    /**
     * Determines whether an overlay can be displayed.
     *
     * @param overlayName the name of the overlay to check
     * @return true if the overlay can be displayed; false otherwise
     */
    private boolean canShowOverlay(String overlayName) {
        AbstractModalOverlay overlay = overlays.get(overlayName);

        return overlay != null
                && !visibleOverlays.contains(overlayName)
                && scene != null
                && scene.getRoot() instanceof StackPane;
    }

    /**
     * Brings all currently visible top-layer overlays to the front.
     */
    private void bringTopLayerOverlaysToFront() {
        for (String topName : topLayerOverlays) {
            AbstractModalOverlay topOverlay = overlays.get(topName);

            if (visibleOverlays.contains(topName)) {
                topOverlay.toFront();
            }
        }
    }

    /**
     * Hides a registered modal overlay.
     *
     * @param overlayName the name of the overlay to hide
     */
    public void hideOverlay(String overlayName) {
        if (canHideOverlay(overlayName)) {
            AbstractModalOverlay overlay = overlays.get(overlayName);
            StackPane root = (StackPane) scene.getRoot();

            root.getChildren().remove(overlay);

            overlay.setVisible(false);
            overlay.setManaged(false);
            visibleOverlays.remove(overlayName);
        }
    }

    /**
     * Determines whether an overlay can be hidden.
     *
     * @param overlayName the name of the overlay to check
     * @return true if the overlay can be hidden; false otherwise
     */
    private boolean canHideOverlay(String overlayName) {
        AbstractModalOverlay overlay = overlays.get(overlayName);

        return overlay != null
                && visibleOverlays.contains(overlayName)
                && scene != null
                && scene.getRoot() instanceof StackPane;
    }

    /**
     * Hides all visible overlays that are not designated as top-layer overlays.
     */
    private void hideAllNonTopLayerOverlays() {
        Set<String> visibleCopy = new HashSet<>(visibleOverlays);

        for (String overlayName : visibleCopy) {
            if (!topLayerOverlays.contains(overlayName)) {
                hideOverlay(overlayName);
            }
        }
    }

    /**
     * Hides all currently visible overlays.
     */
    private void hideAllOverlays() {
        Set<String> visibleCopy = new HashSet<>(visibleOverlays);

        for (String overlayName : visibleCopy) {
            hideOverlay(overlayName);
        }
    }

    /**
     * Returns the currently displayed view.
     *
     * @return the current JavaFX view
     */
    public Parent getCurrentView() {
        return currentView;
    }

    /**
     * Returns the name of the currently displayed view.
     *
     * @return the current view name
     */
    public String getCurrentViewName() {
        return currentViewName;
    }
}
