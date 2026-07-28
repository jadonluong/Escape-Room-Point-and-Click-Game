package view.puzzle;

import interface_adapter.Puzzle.EnterExit.EnterExitViewModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PuzzleView extends StackPane implements ActionListener, PropertyChangeListener {
    private static final double DESIGN_WIDTH = 959; // Roughly same scale as dimensions in MainMenuView
    private static final double DESIGN_HEIGHT = 673;

    private EnterExitViewModel enterExitViewModel;

    private final Pane fixedRoot = new Pane();

    public PuzzleView(EnterExitViewModel enterExitViewModel) {
        this.enterExitViewModel = enterExitViewModel;

        // TODO: Finish this.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No need.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Implement this.
    }
}
