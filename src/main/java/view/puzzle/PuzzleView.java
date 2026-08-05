package view.puzzle;

import interface_adapter.Puzzle.EnterExit.EnterExitController;
import interface_adapter.Puzzle.EnterExit.EnterExitState;
import interface_adapter.Puzzle.EnterExit.EnterExitViewModel;
import interface_adapter.Puzzle.Solve.SolveController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class PuzzleView extends StackPane implements ActionListener, PropertyChangeListener {
    private static final double DESIGN_WIDTH = 959; // Roughly same scale as dimensions in MainMenuView
    private static final double DESIGN_HEIGHT = 673;

    private EnterExitViewModel enterExitViewModel;

    private final Pane fixedRoot = new Pane();
    private ImageView lockImage =  new ImageView();
    private Label puzzleLabel = new Label();
    private ImageView passwordBarImage = new ImageView();
    private Label nameLabel = new Label();
    private Label descriptionLabel = new Label();
    private Label hintLabel = new Label();

    public PuzzleView(EnterExitViewModel enterExitViewModel, EnterExitController enterExitController,
                      SolveController solveController) {
        this.enterExitViewModel = enterExitViewModel;
        this.enterExitViewModel.addPropertyChangeListener(this);
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

        // Answer Field
        double answerFieldHeight = (DESIGN_HEIGHT - (gap * 2)) / 11.0;
        double answerFieldWidth = DESIGN_WIDTH * (7.0 / 12.0) - ((gap * 2) * (1.0/3.0) + answerFieldHeight);

        TextField answerField = new TextField();
        answerField.setPromptText("Type your answer here...");
        answerField.setPrefSize(answerFieldWidth, answerFieldHeight);
        answerField.setStyle(
                "-fx-background-color: #555555; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 20px; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 3; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        );
        answerField.setLayoutX(gap);
        answerField.setLayoutY(DESIGN_HEIGHT - (answerFieldHeight + gap));
        fixedRoot.getChildren().add(answerField);
        // -------------

        // Answer Button

        Button answerButton = new Button("✓");
        answerButton.setPrefSize(answerFieldHeight, answerFieldHeight);
        answerButton.setStyle("-fx-background-color: #2a2a2a; " + "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 20px; " + "-fx-font-weight: bold; " + "-fx-cursor: hand; " +
                "-fx-border-color: #ffffff; " + "-fx-border-width: 3;"
        );
        answerButton.setLayoutX(gap + answerFieldWidth + (gap * 2) * (1.0/3.0));
        answerButton.setLayoutY(DESIGN_HEIGHT - (answerFieldHeight + gap));

        answerButton.setOnAction(e -> solveController.solve(enterExitState.getPuzzleId(),
                answerField.getText(), enterExitState.getInteractableId()));

        fixedRoot.getChildren().add(answerButton);
        // -------------

        // Puzzle Box
        double puzzleBoxWidth = DESIGN_WIDTH * (7.0 / 12.0);
        double puzzleBoxHeight = DESIGN_HEIGHT - (answerFieldHeight + (gap * 3));

        VBox puzzleBoxContainerBox = new VBox(10);
        puzzleBoxContainerBox.setAlignment(Pos.CENTER);
        puzzleBoxContainerBox.setLayoutX(gap);
        puzzleBoxContainerBox.setLayoutY(gap);
        puzzleBoxContainerBox.setPrefSize(puzzleBoxWidth, puzzleBoxHeight);
        puzzleBoxContainerBox.setMaxSize(puzzleBoxWidth, puzzleBoxHeight);

        Rectangle puzzleBox = new Rectangle(puzzleBoxWidth, puzzleBoxHeight);
        puzzleBox.setFill(Color.web("#2a2a2a"));
        puzzleBox.setStroke(Color.WHITE);
        puzzleBox.setStrokeWidth(3);

        lockImage.setImage(loadImage("/images/puzzleview/lock.png"));
        lockImage.setFitWidth(puzzleBoxWidth * 0.25);
        lockImage.setFitHeight(puzzleBoxHeight * 0.25);
        lockImage.setPreserveRatio(true);

        puzzleLabel.setTextFill(Color.WHITE);
        puzzleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 38));
        puzzleLabel.setWrapText(true);
        puzzleLabel.setAlignment(Pos.CENTER);
        puzzleLabel.setPrefWidth(puzzleBox.getWidth() - 50);
        puzzleLabel.setMaxWidth(puzzleBox.getWidth() - 50);
        puzzleLabel.setPrefHeight(puzzleBox.getHeight() * 0.15);
        puzzleLabel.setMaxHeight(puzzleBox.getHeight() * 0.15);

        passwordBarImage.setImage(loadImage("/images/puzzleview/password_bar.png"));
        passwordBarImage.setFitWidth(puzzleBoxWidth * 0.35);
        passwordBarImage.setFitHeight(puzzleBoxHeight * 0.2);
        passwordBarImage.setPreserveRatio(true);

        puzzleBoxContainerBox.getChildren().addAll(lockImage, puzzleLabel, passwordBarImage);

        StackPane puzzleBoxContainer = new StackPane();
        puzzleBoxContainer.getChildren().addAll(puzzleBox, puzzleBoxContainerBox);
        puzzleBoxContainer.setLayoutX(gap);
        puzzleBoxContainer.setLayoutY(gap);
        puzzleBoxContainer.setPrefSize(puzzleBoxWidth, puzzleBoxHeight);

        fixedRoot.getChildren().add(puzzleBoxContainer);
        // ----------

        double rightSideBoxesLayoutX = puzzleBoxWidth + (gap * 2);
        double rightSideBoxesWidth = DESIGN_WIDTH - (puzzleBoxWidth + (gap * 3));

        // Puzzle Name
        double nameBoxHeight = gap * 1.5;
        double nameBoxWidth = rightSideBoxesWidth - (nameBoxHeight + (gap * (3.0 / 4.0)));

        Rectangle nameBox = new Rectangle(nameBoxWidth, nameBoxHeight);
        nameBox.setFill(Color.web("#2a2a2a"));
        nameBox.setStroke(Color.web("#ffffff"));
        nameBox.setStrokeWidth(3);
        nameBox.setLayoutX(rightSideBoxesLayoutX);
        nameBox.setLayoutY(gap);
        fixedRoot.getChildren().add(nameBox);

        nameLabel.setTextFill(Color.web("#ffffff"));
        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        nameLabel.setAlignment(Pos.TOP_LEFT);
        nameLabel.setPrefWidth(nameBox.getWidth() - 24);
        nameLabel.setPrefHeight(nameBox.getHeight() - 24);
        nameLabel.setMaxWidth(nameBox.getWidth() - 24);
        nameLabel.setMaxHeight(nameBox.getHeight() - 24);
        nameLabel.setLayoutX(nameBox.getLayoutX() + 12);
        nameLabel.setLayoutY(nameBox.getLayoutY() + 12);
        fixedRoot.getChildren().add(nameLabel);
        // ---------------------

        // Exit Button
        Button exitButton = new Button("X");
        exitButton.setPrefSize(nameBoxHeight, nameBoxHeight);
        exitButton.setStyle("-fx-background-color: #2a2a2a; " + "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 20px; " + "-fx-font-weight: bold; " + "-fx-cursor: hand; " +
                "-fx-border-color: #ffffff; " + "-fx-border-width: 3;"
        );

        exitButton.setLayoutX(DESIGN_WIDTH - (gap + nameBoxHeight));
        exitButton.setLayoutY(gap);

        exitButton.setOnAction(e -> enterExitController.exit());

        fixedRoot.getChildren().add(exitButton);
        // --------------

        // Hint Box
        double hintBoxHeight = rightSideBoxesWidth + (gap * 2);

        Rectangle hintBox = new Rectangle(rightSideBoxesWidth, hintBoxHeight);
        hintBox.setFill(Color.web("#2a2a2a"));
        hintBox.setStroke(Color.web("#ffffff"));
        hintBox.setStrokeWidth(3);
        hintBox.setLayoutX(rightSideBoxesLayoutX);
        hintBox.setLayoutY(DESIGN_HEIGHT - (hintBoxHeight + gap));
        fixedRoot.getChildren().add(hintBox);

        hintLabel.setTextFill(Color.web("#ffffff"));
        hintLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        hintLabel.setWrapText(true);
        hintLabel.setAlignment(Pos.TOP_LEFT);
        hintLabel.setPrefWidth(hintBox.getWidth() - 30);
        hintLabel.setPrefHeight(hintBox.getHeight() - 30);
        hintLabel.setMaxWidth(hintBox.getWidth() - 30);
        hintLabel.setMaxHeight(hintBox.getHeight() - 30);
        hintLabel.setLayoutX(hintBox.getLayoutX() + 15);
        hintLabel.setLayoutY(hintBox.getLayoutY() + 15);
        fixedRoot.getChildren().add(hintLabel);
        // ----------------

        // Puzzle Description
        double descriptionBoxHeight = DESIGN_HEIGHT - (nameBoxHeight + hintBoxHeight + (gap * 4));

        Rectangle descriptionBox = new Rectangle(rightSideBoxesWidth, descriptionBoxHeight);
        descriptionBox.setFill(Color.web("#2a2a2a"));
        descriptionBox.setStroke(Color.web("#ffffff"));
        descriptionBox.setStrokeWidth(3);
        descriptionBox.setLayoutX(rightSideBoxesLayoutX);
        descriptionBox.setLayoutY(nameBoxHeight + (gap * 2));
        fixedRoot.getChildren().add(descriptionBox);

        descriptionLabel.setTextFill(Color.web("#ffffff"));
        descriptionLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.TOP_LEFT);
        descriptionLabel.setPrefWidth(descriptionBox.getWidth() - 30);
        descriptionLabel.setPrefHeight(descriptionBox.getHeight() - 30);
        descriptionLabel.setMaxWidth(descriptionBox.getWidth() - 30);
        descriptionLabel.setMaxHeight(descriptionBox.getHeight() - 30);
        descriptionLabel.setLayoutX(descriptionBox.getLayoutX() + 15);
        descriptionLabel.setLayoutY(descriptionBox.getLayoutY() + 15);
        fixedRoot.getChildren().add(descriptionLabel);
        // ---------------

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());
    }

    private Image loadImage(String resourcePath) {
        if (resourcePath == null) {
            return null;
        }
        java.io.InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }

    private void rescale() {
        double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
        fixedRoot.setScaleX(scale);
        fixedRoot.setScaleY(scale);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No need.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterExitState enterExitState = enterExitViewModel.getState();
        String puzzleType = enterExitState.getPuzzleType();
        if (puzzleType == null) {
            puzzleType = "";
        }

        nameLabel.setText(puzzleType + " Puzzle");
        descriptionLabel.setText(enterExitState.getDescription());

        switch (puzzleType) {
            case "Anagram":
                hintLabel.setText(enterExitState.getHint());

                puzzleLabel.setText(enterExitState.getScrambled());
                puzzleLabel.setVisible(true);
                puzzleLabel.setManaged(true);
                lockImage.setVisible(true);
                lockImage.setManaged(true);
                passwordBarImage.setVisible(true);
                passwordBarImage.setManaged(true);
                break;
            case "Cryptogram":
                Map<String, String> cipher = enterExitState.getCipher();
                StringBuilder hintText = new StringBuilder();
                if (cipher != null) {
                    for (Map.Entry<String, String> entry : cipher.entrySet()) {
                        hintText.append(entry.getKey()).append(" → ").append(entry.getValue()).append("   ");
                    }
                    hintLabel.setText(hintText.toString());
                }

                puzzleLabel.setText(enterExitState.getEncrypted());
                puzzleLabel.setVisible(true);
                puzzleLabel.setManaged(true);
                lockImage.setVisible(true);
                lockImage.setManaged(true);
                passwordBarImage.setVisible(true);
                passwordBarImage.setManaged(true);
                break;
            case "CodeLock":
                hintLabel.setText(enterExitState.getHint());

                puzzleLabel.setVisible(false);
                puzzleLabel.setManaged(false);
                lockImage.setVisible(true);
                lockImage.setManaged(true);
                passwordBarImage.setVisible(true);
                passwordBarImage.setManaged(true);
                break;
        }
    }
}
