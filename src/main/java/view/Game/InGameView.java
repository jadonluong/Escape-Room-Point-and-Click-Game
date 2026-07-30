package view.Game;

import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InGameView extends StackPane implements PropertyChangeListener {

    private final InGameViewModel viewModel;

    // Different layers
    private final Pane backgroundPane = new Pane();
    private final Pane gamePane = new Pane();
    private final Pane rootPane = new Pane();

    private final GameRenderer renderer;
    private final ImgLoader imgLoader = new ImgLoader();

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    public InGameView(InGameViewModel viewModel, ActionTriggerController actionTriggerController) {

        this.viewModel = viewModel;

        rootPane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        rootPane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        rootPane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(imgLoader.loadImage("/images/rooms/PrisonBackground.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);

        this.renderer = new GameRenderer(gamePane, actionTriggerController);


        viewModel.addPropertyChangeListener(this);


        // Layer order matters
        getChildren().add(rootPane);
        rootPane.getChildren().add(bg);
        rootPane.getChildren().add(gamePane);

        // Rescale
        getChildren().add(rootPane);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getNewValue() instanceof InGameState newState) {
            renderer.renderAll(newState);
        }


    }


    private void rescale() {
        double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
        rootPane.setScaleX(scale);
        rootPane.setScaleY(scale);
    }
}