package view.interactable;

import interface_adapter.Interactable.Interact.InteractController;
import interface_adapter.Interactable.Zoom.ZoomController;
import interface_adapter.Interactable.Zoom.ZoomState;
import interface_adapter.Interactable.Zoom.ZoomViewModel;

import interface_adapter.Puzzle.EnterExit.EnterExitController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class ZoomView extends StackPane implements ActionListener, PropertyChangeListener {
    private static final double DESIGN_WIDTH = 959; // Roughly same scale as dimensions in MainMenuView
    private static final double DESIGN_HEIGHT = 673;

    private ZoomViewModel zoomViewModel;

    private final Pane fixedRoot = new Pane();
    private Label nameLabel = new Label();
    private Label descriptionLabel = new Label();
    private ImageView spriteImageView = new ImageView();
    private Button interactButton = new Button();
    private Rectangle interactBox = new Rectangle();
    private Label interactBoxLabel = new Label();

    public ZoomView(ZoomController zoomController, ZoomViewModel zoomViewModel, InteractController interactController,
                    EnterExitController enterExitController) {
        this.zoomViewModel = zoomViewModel;
        ZoomState zoomState = zoomViewModel.getState();

        // Background
        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        Rectangle background = new Rectangle(DESIGN_WIDTH, DESIGN_HEIGHT);
        background.setFill(Color.web("#2a2a2a")); // Dark gray
        fixedRoot.getChildren().add(background);
        // -----------

        double gap = DESIGN_HEIGHT / 20.0; // Size of the gap between all the main boxes

        // Interactable Sprite
        double spriteBoxWidth = DESIGN_WIDTH * (7.0 / 12.0);
        double spriteBoxHeight = DESIGN_HEIGHT - (gap * 2);

        Rectangle spriteBox = new Rectangle(spriteBoxWidth, spriteBoxHeight);
        spriteBox.setFill(Color.web("#2a2a2a"));
        spriteBox.setStroke(Color.web("#ffffff")); // White
        spriteBox.setStrokeWidth(3);
        spriteBox.setLayoutX(gap);
        spriteBox.setLayoutY(gap);
        fixedRoot.getChildren().add(spriteBox);

        Image spriteImage = loadImage(zoomState.getSprite());
        spriteImageView.setImage(spriteImage);
        spriteImageView.setFitWidth(spriteBox.getWidth() - 40);
        spriteImageView.setFitHeight(spriteBox.getHeight() - 40);
        spriteImageView.setPreserveRatio(true);

        StackPane spriteContainer = new StackPane();
        spriteContainer.setLayoutX(spriteBox.getLayoutX());
        spriteContainer.setLayoutY(spriteBox.getLayoutY());
        spriteContainer.setPrefSize(spriteBoxWidth, spriteBoxHeight);
        spriteContainer.getChildren().add(spriteImageView);
        spriteContainer.setAlignment(Pos.CENTER);
        fixedRoot.getChildren().add(spriteContainer);
        // -------------------

        double rightSideBoxesLayoutX = spriteBoxWidth + (gap * 2);
        double rightSideBoxesWidth = DESIGN_WIDTH - (spriteBoxWidth + (gap * 3));

        // User Inventory Box
        double inventoryBoxHeight = rightSideBoxesWidth; // inventoryBox is a Square

        Rectangle inventoryBox = new Rectangle(rightSideBoxesWidth, inventoryBoxHeight);
        inventoryBox.setFill(Color.web("#2a2a2a"));
        inventoryBox.setStroke(Color.web("#ffffff"));
        inventoryBox.setStrokeWidth(3);
        inventoryBox.setLayoutX(rightSideBoxesLayoutX);
        inventoryBox.setLayoutY(DESIGN_HEIGHT - (inventoryBoxHeight + gap));
        fixedRoot.getChildren().add(inventoryBox);

        // TODO: Make sure to update the text below with the right keybind.
        Label inventoryLabel = new Label("""
                Inventory. A possible feature for extensions to this project.
                
                For now, please use the inventory hotbar (by pressing E) to switch the currently selected item if the \
                player wishes to use a specific item to interact with this object.
                """);
        inventoryLabel.setTextFill(Color.web("#ffffff"));
        inventoryLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        inventoryLabel.setWrapText(true);
        inventoryLabel.setAlignment(Pos.TOP_LEFT);
        inventoryLabel.setPrefWidth(inventoryBox.getWidth() - 30);
        inventoryLabel.setPrefHeight(inventoryBox.getHeight() - 30);
        inventoryLabel.setMaxWidth(inventoryBox.getWidth() - 30);
        inventoryLabel.setMaxHeight(inventoryBox.getHeight() - 30);
        inventoryLabel.setLayoutX(inventoryBox.getLayoutX() + 15);
        inventoryLabel.setLayoutY(inventoryBox.getLayoutY() + 15);
        fixedRoot.getChildren().add(inventoryLabel);
        // ----------------

        // Interact Button
        double interactButtonHeight = (2.0 / 7.0) * (DESIGN_HEIGHT - (inventoryBoxHeight + (gap * 5)));
        String interactLabel = zoomState.getInteractLabel();

        StackPane interactContainer = new StackPane();
        interactContainer.setPrefSize(rightSideBoxesWidth, interactButtonHeight);
        interactContainer.setMaxSize(rightSideBoxesWidth, interactButtonHeight);
        interactContainer.setLayoutX(rightSideBoxesLayoutX);
        interactContainer.setLayoutY(DESIGN_HEIGHT - (inventoryBoxHeight + interactButtonHeight + (gap * 2)));

        interactButton.setPrefSize(rightSideBoxesWidth, interactButtonHeight);
        interactButton.setMaxSize(rightSideBoxesWidth, interactButtonHeight);
        interactButton.setStyle("-fx-background-color: #2a2a2a; " + "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 20px; " + "-fx-font-weight: bold; " + "-fx-cursor: hand; " +
                "-fx-border-color: #ffffff; " + "-fx-border-width: 3;"
        );

        interactButton.setOnAction(e -> {
            if (interactLabel.equals("Enter Puzzle")) {
                enterExitController.enter(zoomState.getUserId(), zoomState.getPuzzleId());
            } else {
                interactController.interact(zoomState.getUserId(), zoomState.getInteractableId());
            }
        });

        interactBox.setWidth(rightSideBoxesWidth);
        interactBox.setHeight(interactButtonHeight);
        interactBox.setFill(Color.web("#2a2a2a"));
        interactBox.setStroke(Color.web("#ffffff"));
        interactBox.setStrokeWidth(3);

        interactBoxLabel.setText("No Action Available");
        interactBoxLabel.setTextFill(Color.web("#ffffff"));
        interactBoxLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        interactBoxLabel.setWrapText(true);

        interactContainer.getChildren().addAll(interactButton, interactBox, interactBoxLabel);

        if (interactLabel != null) {
            interactButton.setText(interactLabel);
            interactButton.setVisible(true);
            interactButton.setManaged(true);
            interactBox.setVisible(false);
            interactBox.setManaged(false);
            interactBoxLabel.setVisible(false);
            interactBoxLabel.setManaged(false);
        } else {
            interactButton.setVisible(false);
            interactButton.setManaged(false);
            interactBox.setVisible(true);
            interactBox.setManaged(true);
            interactBoxLabel.setVisible(true);
            interactBoxLabel.setManaged(true);
        }

        fixedRoot.getChildren().add(interactContainer);
        // ----------------------------

        // Interactable Description
        double descriptionBoxHeight = (3.5 / 7.0) * (DESIGN_HEIGHT - (inventoryBoxHeight + (gap * 5)));

        Rectangle descriptionBox = new Rectangle(rightSideBoxesWidth, descriptionBoxHeight);
        descriptionBox.setFill(Color.web("#2a2a2a"));
        descriptionBox.setStroke(Color.web("#ffffff"));
        descriptionBox.setStrokeWidth(3);
        descriptionBox.setLayoutX(rightSideBoxesLayoutX);
        descriptionBox.setLayoutY(DESIGN_HEIGHT - (inventoryBoxHeight + interactButtonHeight +
                descriptionBoxHeight + (gap * 3)));
        fixedRoot.getChildren().add(descriptionBox);

        descriptionLabel.setText(zoomState.getDescription());
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

        // Interactable Name
        double nameBoxHeight = (1.5 / 7.0) * (DESIGN_HEIGHT - (inventoryBoxHeight + (gap * 5)));
        double nameBoxWidth = rightSideBoxesWidth - (nameBoxHeight + (gap * (3.0 / 4.0)));

        Rectangle nameBox = new Rectangle(nameBoxWidth, nameBoxHeight);
        nameBox.setFill(Color.web("#2a2a2a"));
        nameBox.setStroke(Color.web("#ffffff"));
        nameBox.setStrokeWidth(3);
        nameBox.setLayoutX(rightSideBoxesLayoutX);
        nameBox.setLayoutY(gap);
        fixedRoot.getChildren().add(nameBox);

        nameLabel.setText(zoomState.getName());
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

        // ZoomOut Button
        Button zoomOutButton = new Button("X");
        zoomOutButton.setPrefSize(nameBoxHeight, nameBoxHeight);
        zoomOutButton.setStyle("-fx-background-color: #2a2a2a; " + "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 20px; " + "-fx-font-weight: bold; " + "-fx-cursor: hand; " +
                "-fx-border-color: #ffffff; " + "-fx-border-width: 3;"
        );

        zoomOutButton.setLayoutX(DESIGN_WIDTH - (gap + nameBoxHeight));
        zoomOutButton.setLayoutY(gap);

        zoomOutButton.setOnAction(e -> zoomController.zoomOut());

        fixedRoot.getChildren().add(zoomOutButton);
        // --------------

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());
    }

    private Image loadImage(String resourcePath) {
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
        // Not used.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ZoomState zoomState = zoomViewModel.getState();

        nameLabel.setText(zoomState.getName());
        descriptionLabel.setText(zoomState.getDescription());

        Image spriteImage = loadImage(zoomState.getSprite());
        spriteImageView.setImage(spriteImage);

        String interactLabel = zoomState.getInteractLabel();
        if (interactLabel != null) {
            interactButton.setText(interactLabel);
            interactButton.setVisible(true);
            interactButton.setManaged(true);
            interactBox.setVisible(false);
            interactBox.setManaged(false);
            interactBoxLabel.setVisible(false);
            interactBoxLabel.setManaged(false);
        } else {
            interactButton.setVisible(false);
            interactButton.setManaged(false);
            interactBox.setVisible(true);
            interactBox.setManaged(true);
            interactBoxLabel.setVisible(true);
            interactBoxLabel.setManaged(true);
        }
    }
}
