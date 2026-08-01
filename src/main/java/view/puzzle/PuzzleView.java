package view.puzzle;

import interface_adapter.Puzzle.EnterExit.EnterExitState;
import interface_adapter.Puzzle.EnterExit.EnterExitViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
        /*
        EnterExitState enterExitState = enterExitViewModel.getState();

        // Background
        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        Rectangle background = new Rectangle(DESIGN_WIDTH, DESIGN_HEIGHT);
        background.setFill(Color.web("#2a2a2a")); // Dark gray
        fixedRoot.getChildren().add(background);
        // ----------

        double gap = DESIGN_HEIGHT / 20.0; // Size of the gap between all the main boxes

        // Answer Box
        double answerBoxWidth = DESIGN_WIDTH * (7.0 / 12.0) - (gap * 3);
        double answerBoxHeight = (DESIGN_HEIGHT - (gap * 2)) / 11.0;

        Rectangle answerBox = new Rectangle(answerBoxWidth, answerBoxHeight);
        answerBox.setFill(Color.web("#2a2a2a"));
        answerBox.setStroke(Color.WHITE);
        answerBox.setStrokeWidth(3);
        answerBox.setLayoutX(gap);
        answerBox.setLayoutY(DESIGN_HEIGHT - (answerBoxHeight + gap));
        fixedRoot.getChildren().add(answerBox);
        // -------------

        // Answer Button
        double answerButtonWidth = (gap * 2) * (2.0/3.0);

        Label answerButtonLabel = new Label("✓");

        Button answerButton = new Button();

        // -------------

        // Puzzle Box
        double puzzleBoxWidth = DESIGN_WIDTH * (7.0 / 12.0);
        double puzzleBoxHeight = DESIGN_HEIGHT - (answerBoxHeight + (gap * 3));

        Rectangle puzzleBox = new Rectangle(puzzleBoxWidth, puzzleBoxHeight);
        puzzleBox.setFill(Color.web("#2a2a2a"));
        puzzleBox.setStroke(Color.WHITE);
        puzzleBox.setStrokeWidth(3);
        puzzleBox.setLayoutX(gap);
        puzzleBox.setLayoutY(gap);
        fixedRoot.getChildren().add(puzzleBox);

        if (!enterExitState.getPuzzleType().equals("CodeLock")) { // If not CodeLockPuzzle then needs text.
            String puzzleText = enterExitState.getScrambled();
            if (enterExitState.getEncrypted() != null) { // !CodeLock --> exactly one of scrambled & encrypted !null
                puzzleText = enterExitState.getEncrypted();
            }

            Label puzzleLabel = new Label(puzzleText);
            puzzleLabel.setTextFill(Color.WHITE);
            puzzleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
            puzzleLabel.setWrapText(true);
            puzzleLabel.setAlignment(Pos.CENTER);
            puzzleLabel.setPrefWidth(puzzleBox.getWidth() - 50);
            puzzleLabel.setPrefHeight(puzzleBox.getHeight() - 50);
            puzzleLabel.setMaxWidth(puzzleBox.getWidth() - 50);
            puzzleLabel.setMaxHeight(puzzleBox.getHeight() - 50);
            puzzleLabel.setLayoutX(puzzleBox.getLayoutX() + 25);
            puzzleLabel.setLayoutY(puzzleBox.getLayoutY() + 25);
            fixedRoot.getChildren().add(puzzleLabel);

            // TODO: Add a picture?
        } else {
            // TODO: Add a picture?
        }
        // ----------

        //

        // TODO: Finish this.
        */
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
