package view.Game;

import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InGameView extends StackPane implements PropertyChangeListener {

    private final InGameViewModel viewModel;

    // Different layers
    private final Pane backgroundPane = new Pane();
    private final Pane gamePane = new Pane();

    private final GameRenderer renderer;

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private String imgPath;

    public InGameView(InGameViewModel viewModel,
                      GameMenuView gameMenuView,
                      ActionTriggerController actionTriggerController) {

        this.viewModel = viewModel;
        this.imgPath = "/images/items/prison/spider1.png";

        upDateBackgroundPane(imgPath);

        backgroundPane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        backgroundPane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        backgroundPane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        gamePane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);



        this.renderer = new GameRenderer(gamePane, actionTriggerController);

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

        // Add the Menu, but set it invisible at start.



        // Listen for ESC
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (gameMenuView.isVisible()) {
                    gameMenuView.hide();
                } else {
                    gameMenuView.show();
                }
                event.consume();
            }
        });

        getChildren().addAll(backgroundPane);
    }




    public void upDateBackgroundPane(String imgPath) {
        backgroundPane.getChildren().clear();
        ImageView bg = new ImageView(imgPath);
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        backgroundPane.getChildren().add(bg);
        backgroundPane.getChildren().add(gamePane);
    }

    private void rescale() {
        double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
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
