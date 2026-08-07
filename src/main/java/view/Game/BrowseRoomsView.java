package view.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.Map;

import application.use_cases.game_play.QuickPlay.BrowseRooms.RoomInfo;
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

public class BrowseRoomsView extends StackPane implements PropertyChangeListener {

    // Attribute related to design
    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    // Font
    private static final String FONT = "Arial";
    private static final double TITLE_SIZE = 72;
    private static final double TEXT_SIZE = 36;

    // Title
    private static final double TITLE_POSITION_X = 145;
    private static final double TITLE_POSITION_Y = 120;

    // Quit button
    private static final double QUIT_WIDTH = 500;
    private static final double QUIT_HEIGHT = 190;
    private static final double QUIT_POSITION_X = 50;
    private static final double QUIT_POSITION_Y = 1680;

    // Room Card
    private static final double ROOM_CARD_START_X = 145;
    private static final double CARD_WIDTH = 2600;
    private static final double CARD_HEIGHT = 340;
    private static final double CARD_SPACING = 60;
    private static final double ARC_WIDTH = 30;
    private static final double ARC_HEIGHT = 30;

    private static final double OPACITY = 0.3;
    private static final double STROKE_WIDTH = 3;
    private static final double THUMBNAIL_SIZE = 40;
    private static final double THUMBNAIL_X = 20;
    private static final double THUMBNAIL_Y = 20;
    private static final double BUTTON_MOUSE_ENTER_SCALE = 1.05;
    private static final double BUTTON_MOUSE_EXIT_SCALE = 1.00;
    private static final double ROOM_NAME_FONT_SIZE = 56;
    private static final double ROOM_NAME_X = 60;
    private static final double ROOM_NAME_Y = 30;
    private static final double DESCRIPTION_FONT_SIZE = 32;
    private static final double DESCRIPTION_MAX_WIDTH = 700;
    private static final double DESCRIPTION_X = 60;
    private static final double DESCRIPTION_Y = 110;
    private static final double PLAY_BUTTON_WIDTH = 400;
    private static final double PLAY_BUTTON_HEIGHT = 150;
    private static final double PLAY_BUTTON_X_OFFSET = 450;
    private static final double PLAY_BUTTON_Y_OFFSET = 150;

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
        final Rectangle background = new Rectangle(DESIGN_WIDTH, DESIGN_HEIGHT);
        background.setFill(Color.web("#1a1a2e"));
        fixedRoot.getChildren().add(background);

        final Label title = new Label("CHOOSE YOUR ESCAPE ROOM");
        title.setFont(Font.font(FONT, FontWeight.BOLD, TITLE_SIZE));
        title.setTextFill(Color.WHITE);
        title.setLayoutX(TITLE_POSITION_X);
        title.setLayoutY(TITLE_POSITION_Y);
        fixedRoot.getChildren().add(title);

        errorLabel.setFont(Font.font(FONT, FontWeight.BOLD, TEXT_SIZE));
        errorLabel.setTextFill(Color.web("#ff6b6b"));
        errorLabel.setLayoutX(TITLE_POSITION_X);
        errorLabel.setLayoutY(TITLE_POSITION_Y);
        fixedRoot.getChildren().add(errorLabel);

        fixedRoot.getChildren().add(
                makeButton("/images/ui/buttons/QuitButton.png", QUIT_WIDTH,
                        QUIT_HEIGHT, QUIT_POSITION_X, QUIT_POSITION_Y,
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

        final Map<String, RoomInfo> roomInfos = state.getRoomInfo();
        if (roomInfos == null || roomInfos.isEmpty()) {
            return;
        }

        int i = 0;
        for (Map.Entry<String, RoomInfo> entry : roomInfos.entrySet()) {
            final String roomId = entry.getKey();
            final RoomInfo roomInfo = entry.getValue();
            final double currentY = ROOM_CARD_START_X + (i * (CARD_HEIGHT + CARD_SPACING));

            final Pane roomCard = createRoomCard(roomId, roomInfo, ROOM_CARD_START_X, currentY, CARD_WIDTH, CARD_HEIGHT);
            roomCardsContainer.getChildren().add(roomCard);

            i++;
        }
    }

    private Pane createRoomCard(String roomId, RoomInfo info, double xPosition, double yPosition, double width, double height) {
        final Pane card = new Pane();
        card.setLayoutX(xPosition);
        card.setLayoutY(yPosition);
        card.setPrefSize(width, height);

        final Rectangle cardBg = new Rectangle(width, height);
        cardBg.setArcWidth(ARC_WIDTH);
        cardBg.setArcHeight(ARC_HEIGHT);
        cardBg.setFill(Color.web("#2a2a45"));
        cardBg.setStroke(Color.web("#ffffff", OPACITY));
        cardBg.setStrokeWidth(STROKE_WIDTH);

        final double thumbnailSize = height - THUMBNAIL_SIZE;
        final ImageView thumbnail = new ImageView(loadImage(info.imagePath()));
        thumbnail.setFitWidth(thumbnailSize);
        thumbnail.setFitHeight(thumbnailSize);
        thumbnail.setLayoutX(THUMBNAIL_X);
        thumbnail.setLayoutY(THUMBNAIL_Y);

        // RoomInfo has no display name, so derive a friendlier title from the ID
        // (e.g. "prison" -> "Prison") rather than showing raw internal data.
        final String displayName = roomId.substring(0, 1).toUpperCase() + roomId.substring(1);

        final Label nameLabel = new Label(displayName);
        nameLabel.setFont(Font.font(FONT, FontWeight.BOLD, ROOM_NAME_FONT_SIZE));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setLayoutX(thumbnailSize + ROOM_NAME_X);
        nameLabel.setLayoutY(ROOM_NAME_Y);

        final Label descriptionLabel = new Label(info.description());
        descriptionLabel.setFont(Font.font(FONT, FontWeight.NORMAL, DESCRIPTION_FONT_SIZE));
        descriptionLabel.setTextFill(Color.web("#cccccc"));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(width - thumbnailSize - DESCRIPTION_MAX_WIDTH);
        descriptionLabel.setLayoutX(thumbnailSize + DESCRIPTION_X);
        descriptionLabel.setLayoutY(DESCRIPTION_Y);

        final ImageView playButton = makeButton(
                "/images/ui/buttons/QuickButton.png",
                PLAY_BUTTON_WIDTH,
                PLAY_BUTTON_HEIGHT,
                width - PLAY_BUTTON_X_OFFSET,
                (height - PLAY_BUTTON_Y_OFFSET) / 2,
                () -> quickModeStartUpController.execute(roomId)
        );

        card.getChildren().addAll(cardBg, thumbnail, nameLabel, descriptionLabel, playButton);
        return card;
    }

    private ImageView makeButton(String resourcePath, double imgWidth, double imgHeight,
                                 double xPosition, double yPosition, Runnable onClick) {
        final ImageView button = new ImageView(loadImage(resourcePath));
        button.setFitWidth(imgWidth);
        button.setFitHeight(imgHeight);
        button.setLayoutX(xPosition);
        button.setLayoutY(yPosition);

        button.setPickOnBounds(true);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(e -> onClick.run());

        button.setOnMouseEntered(e -> {
            button.setScaleX(BUTTON_MOUSE_ENTER_SCALE);
            button.setScaleY(BUTTON_MOUSE_ENTER_SCALE);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(BUTTON_MOUSE_EXIT_SCALE);
            button.setScaleY(BUTTON_MOUSE_EXIT_SCALE);
        });

        return button;
    }

    private Image loadImage(String resourcePath) {
        final InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }

    private void rescale() {
        final double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
        fixedRoot.setScaleX(scale);
        fixedRoot.setScaleY(scale);
    }
}
