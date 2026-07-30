package view.Game;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.RoomInfo;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsViewModel;
import interface_adapter.GamePlay.QuickModeStartUp.QuickModeStartUpController;
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
import java.util.Map;

public class BrowseRoomsView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();
    private final StackPane previousView;

    private final ViewManager viewManager;
    private final BrowseRoomsViewModel viewModel;
    private final QuickModeStartUpController quickModeStartUpController;

    private final Pane roomCardsContainer = new Pane();
    private final Label errorLabel = new Label();

    public BrowseRoomsView(ViewManager viewManager, StackPane previousView, BrowseRoomsViewModel viewModel,
                           QuickModeStartUpController quickModeStartUpController) {
        this.viewManager = viewManager;
        this.previousView = previousView;
        this.viewModel = viewModel;
        this.quickModeStartUpController = quickModeStartUpController;

        this.viewModel.addPropertyChangeListener(this);

        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        // Solid dark background instead of the busy main-menu collage —
        // much better contrast for a text-heavy list screen.
        Rectangle background = new Rectangle(DESIGN_WIDTH, DESIGN_HEIGHT);
        background.setFill(Color.web("#1a1a2e"));
        fixedRoot.getChildren().add(background);

        Label title = new Label("CHOOSE YOUR ESCAPE ROOM");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 72));
        title.setTextFill(Color.WHITE);
        title.setLayoutX(145);
        title.setLayoutY(120);
        fixedRoot.getChildren().add(title);

        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        errorLabel.setTextFill(Color.web("#ff6b6b"));
        errorLabel.setLayoutX(145);
        errorLabel.setLayoutY(220);
        fixedRoot.getChildren().add(errorLabel);

        fixedRoot.getChildren().add(
                makeButton("/images/ui/buttons/QuitButton.png", 500, 1900, 2200, 100,
                        () -> this.viewManager.show(this.previousView))
        );

        fixedRoot.getChildren().add(roomCardsContainer);

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());

        updateRooms(viewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof BrowseRoomsState newState) {
            updateRooms(newState);
        }
    }

    private void updateRooms(BrowseRoomsState state) {
        roomCardsContainer.getChildren().clear();
        errorLabel.setText("");

        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            errorLabel.setText(state.getErrorMessage());
            return;
        }

        Map<String, RoomInfo> roomInfos = state.getRoomInfo();
        if (roomInfos == null || roomInfos.isEmpty()) {
            return;
        }

        double startX = 145;
        double startY = 320;
        double cardWidth = 2600;
        double cardHeight = 340;
        double spacingY = 60;

        int i = 0;
        for (Map.Entry<String, RoomInfo> entry : roomInfos.entrySet()) {
            String roomId = entry.getKey();
            RoomInfo roomInfo = entry.getValue();
            double currentY = startY + (i * (cardHeight + spacingY));

            Pane roomCard = createRoomCard(roomId, roomInfo, startX, currentY, cardWidth, cardHeight);
            roomCardsContainer.getChildren().add(roomCard);

            i++;
        }
    }

    private Pane createRoomCard(String roomId, RoomInfo info, double x, double y, double width, double height) {
        Pane card = new Pane();
        card.setLayoutX(x);
        card.setLayoutY(y);
        card.setPrefSize(width, height);

        Rectangle cardBg = new Rectangle(width, height);
        cardBg.setArcWidth(30);
        cardBg.setArcHeight(30);
        cardBg.setFill(Color.web("#2a2a45"));
        cardBg.setStroke(Color.web("#ffffff", 0.3));
        cardBg.setStrokeWidth(3);

        double thumbnailSize = height - 40;
        ImageView thumbnail = new ImageView(loadImage(info.imagePath()));
        thumbnail.setFitWidth(thumbnailSize);
        thumbnail.setFitHeight(thumbnailSize);
        thumbnail.setLayoutX(20);
        thumbnail.setLayoutY(20);

        // RoomInfo has no display name, so derive a friendlier title from the ID
        // (e.g. "prison" -> "Prison") rather than showing raw internal data.
        String displayName = roomId.substring(0, 1).toUpperCase() + roomId.substring(1);

        Label nameLabel = new Label(displayName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 56));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setLayoutX(thumbnailSize + 60);
        nameLabel.setLayoutY(30);

        Label descriptionLabel = new Label(info.description());
        descriptionLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 32));
        descriptionLabel.setTextFill(Color.web("#cccccc"));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(width - thumbnailSize - 700);
        descriptionLabel.setLayoutX(thumbnailSize + 60);
        descriptionLabel.setLayoutY(110);

        ImageView playButton = makeButton("/images/ui/buttons/QuickButton.png", 400, 150,
                width - 450, (height - 150) / 2, () -> quickModeStartUpController.execute(roomId));

        card.getChildren().addAll(cardBg, thumbnail, nameLabel, descriptionLabel, playButton);
        return card;
    }

    private void onRoomSelected(String roomId) {
        quickModeStartUpController.execute(roomId);
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