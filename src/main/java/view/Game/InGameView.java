package view.Game;

import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InGameView extends StackPane implements PropertyChangeListener {

    private final InGameViewModel viewModel;
    private final GameMenuView gameMenuView;

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
        this.gameMenuView = gameMenuView;
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

        // Add the Menu, but set it invisible at start.
        gameMenuView.setVisible(false);
        gameMenuView.setManaged(false);

        getChildren().addAll(backgroundPane, gameMenuView);
    }

    private void toggleMenu() {
        boolean showing = gameMenuView.isVisible();

        gameMenuView.setVisible(!showing);
        gameMenuView.setManaged(!showing);

        if (showing) {
            requestFocus();   // return keyboard focus to the game
        }
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
