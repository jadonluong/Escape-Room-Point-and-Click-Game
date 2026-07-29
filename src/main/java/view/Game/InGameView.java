package view.Game;

import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.GamePlay.InGameViewModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InGameView extends StackPane implements PropertyChangeListener {

    private final InGameViewModel viewModel;

    // Different layers
    private final Pane backgroundPane = new Pane();
    private final Pane objectPane = new Pane();
    private final Pane uiPane = new Pane();

    private final GameRenderer renderer;


    public InGameView(InGameViewModel viewModel) {

        this.viewModel = viewModel;

        this.renderer = new GameRenderer(objectPane);

        viewModel.addPropertyChangeListener(this);


        // Layer order matters
        getChildren().addAll(
                backgroundPane,
                objectPane,
                uiPane
        );
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
