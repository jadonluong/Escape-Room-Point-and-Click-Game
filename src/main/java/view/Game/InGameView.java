package view.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.inventory.InventoryOverlay;

public class InGameView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final InGameViewModel viewModel;
    private final GameMenuView gameMenuView;
    private final InventoryOverlay inventoryOverlay;
    private final GameObjectActionDispatcher actionDispatcher;

    // Different layers
    private final Pane backgroundPane = new Pane();
    private final Pane gamePane = new Pane();

    private final GameRenderer renderer;

    private String imgPath;

    public InGameView(InGameViewModel viewModel,
                      GameMenuView gameMenuView,
                      InventoryOverlay inventoryOverlay, GameObjectActionDispatcher actionDispatcher) {

        this.viewModel = viewModel;
        this.gameMenuView = gameMenuView;
        this.inventoryOverlay = inventoryOverlay;
        this.actionDispatcher = actionDispatcher;
        this.imgPath = "/images/items/prison/spider1.png";

        upDateBackgroundPane(imgPath);

        backgroundPane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        backgroundPane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        backgroundPane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        gamePane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        this.renderer = new GameRenderer(gamePane, actionDispatcher);

        viewModel.addPropertyChangeListener(this);

        setAlignment(javafx.geometry.Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());

        // Allow this view to receive keyboard input
        setFocusTraversable(true);

        // Request focus when this view is added to the scene
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                requestFocus();
            }
        });

        // Listen for ESC/ENTER
        this.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.out.println("ESC pressed!");
                if (gameMenuView.isVisible()) {
                    gameMenuView.hide();
                }
                else {
                    gameMenuView.show();
                }
                event.consume();
            }
            else if (event.getCode() == KeyCode.ENTER) {
                System.out.println("'ENTER' key pressed! Toggling inventory...");
                if (inventoryOverlay.isVisible()) {
                    inventoryOverlay.hide();
                }
                else {
                    inventoryOverlay.show();
                }
                event.consume();
            }
        });
        getChildren().addAll(backgroundPane);
    }

    /**
     * Updates the background pane with a new background image.
     *
     * @param imgPath the path of the background image to display
     */
    public void upDateBackgroundPane(String imgPath) {
        backgroundPane.getChildren().clear();
        final ImageView bg = new ImageView(imgPath);
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        backgroundPane.getChildren().add(bg);
        backgroundPane.getChildren().add(gamePane);
    }

    private void rescale() {
        final double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
        backgroundPane.setScaleX(scale);
        backgroundPane.setScaleY(scale);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getNewValue() instanceof InGameState newState) {
            renderer.renderAll(newState);
            upDateBackgroundPane(newState.getImgPath());
        }

    }

}
