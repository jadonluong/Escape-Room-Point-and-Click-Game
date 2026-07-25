package view.InGame;

import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsViewModel;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import view.ViewManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.List;

public class BrowseRoomsView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();
    private final ViewManager viewManager;
    private final StackPane previousView;
    private final BrowseRoomsViewModel viewModel;

    private final Pane roomCardsContainer = new Pane();
    private final Label errorLabel = new Label();

    public BrowseRoomsView(ViewManager viewManager, StackPane previousView, BrowseRoomsViewModel viewModel) {
        this.viewManager = viewManager;
        this.previousView = previousView;
        this.viewModel = viewModel;

        // Register view as listener to ViewModel changes
        this.viewModel.addPropertyChangeListener(this);

        // Canvas setup
        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        // Background
        ImageView bg = new ImageView(loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        fixedRoot.getChildren().add(bg);

        // Title
        Label title = new Label("CHOOSE YOUR ESCAPE ROOM");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 72));
        title.setTextFill(Color.WHITE);
        title.setLayoutX(145);
        title.setLayoutY(120);
        fixedRoot.getChildren().add(title);

        // Error Message Display
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        errorLabel.setTextFill(Color.RED);
        errorLabel.setLayoutX(145);
        errorLabel.setLayoutY(220);
        fixedRoot.getChildren().add(errorLabel);

        // Back Button
        fixedRoot.getChildren().add(
                makeButton("/images/ui/buttons/QuitButton.png", 500, 190, 2200, 100,
                        () -> this.viewManager.show(this.previousView))
        );

        // Add container that will hold dynamically rendered room cards
        fixedRoot.getChildren().add(roomCardsContainer);

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        // Scaling listeners
        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());

        // Render initial state
        updateRooms(viewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Triggered when Presenter calls viewModel.firePropertyChanged()
        if (evt.getNewValue() instanceof BrowseRoomsState newState) {
            updateRooms(newState);
        }
    }

    private void updateRooms(BrowseRoomsState state) {
        // Clear previous errors
        errorLabel.setText("");

        // Handle Error State
        if (state.getError() != null && !state.getError().isEmpty()) {
            errorLabel.setText(state.getError());
            return;
        }

        List<String> roomIds = state.getRoomIds();
        if (roomIds == null || roomIds.isEmpty()) {
            return;
        }

        // Render Cards dynamically based on IDs delivered from Presenter
        double startX = 145;
        double startY = 320;
        double cardWidth = 2600;
        double cardHeight = 280;
        double spacingY = 40;

        for (int i = 0; i < roomIds.size(); i++) {
            String roomId = roomIds.get(i);
            double currentY = startY + (i * (cardHeight + spacingY));

            Pane roomCard = createRoomCard(roomId, startX, currentY, cardWidth, cardHeight);
            roomCardsContainer.getChildren().add(roomCard);
        }
    }

    private Pane createRoomCard(String roomId, double x, double y, double width, double height) {
        Pane card = new Pane();
        card.setLayoutX(x);
        card.setLayoutY(y);
        card.setPrefSize(width, height);

        Rectangle cardBg = new Rectangle(width, height);
        cardBg.setArcWidth(30);
        cardBg.setArcHeight(30);
        cardBg.setFill(Color.web("#000000", 0.65));
        cardBg.setStroke(Color.web("#ffffff", 0.3));
        cardBg.setStrokeWidth(3);

        Label nameLabel = new Label("Room ID: " + roomId);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setLayoutX(50);
        nameLabel.setLayoutY(40);

        //TODO: This might need to change if we want to display different img for each choice.
        ImageView playButton = makeButton("Button", 500, 180, 2030, 50, () -> {
            onRoomSelected(roomId);
        });

        card.getChildren().addAll(cardBg, nameLabel, playButton);
        return card;
    }

    private void onRoomSelected(String roomId) {
        //TODO: Change to game view
    }

    private ImageView makeButton(String resourcePath, double imgWidth, double imgHeight,
                                 double x, double y, Runnable onClick) {
        ImageView button = new ImageView(loadImage(resourcePath));
        button.setFitWidth(imgWidth);
        button.setFitHeight(imgHeight);
        button.setLayoutX(x);
        button.setLayoutY(y);

        button.setPickOnBounds(true);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(e -> onClick.run());

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        return button;
    }

    private Image loadImage(String resourcePath) {
        InputStream stream = getClass().getResourceAsStream(resourcePath);
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
}