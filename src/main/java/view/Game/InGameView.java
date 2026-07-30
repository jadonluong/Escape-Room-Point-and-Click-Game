package view.Game;

import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
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
    private final Pane uiPane = new Pane();

    private final GameRenderer renderer;
    private final ImgLoader imgLoader = new ImgLoader();

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    public InGameView(InGameViewModel viewModel, ActionTriggerController actionTriggerController) {

        this.viewModel = viewModel;

        gamePane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        gamePane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(imgLoader.loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        gamePane.getChildren().add(bg);

        this.renderer = new GameRenderer(gamePane, actionTriggerController);


        viewModel.addPropertyChangeListener(this);


        // Layer order matters
        getChildren().add(gamePane);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getNewValue() instanceof InGameState newState) {
            renderer.renderAll(newState);
        }


    }


    public Pane getBackgroundPane() {
        return backgroundPane;
    }


    public Pane getUiPane() {
        return uiPane;
    }
}
