package view.main_menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import interface_adapter.game_play.browse_rooms.BrowseRoomsController;
import interface_adapter.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpController;
import interface_adapter.user.logged_in.LoggedInViewModel;
import interface_adapter.user.main_menu.MainMenuViewModel;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import view.common.AbstractModalOverlay;
import view.common.AudioControlView;

/**
 * Displays the main menu and manages navigation between the available game
 * modes and user authentication overlays.
 *
 * <p>The menu uses a fixed design size and scales to fit the available window
 * while preserving the original proportions.</p>
 */
public class MainMenuView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private static final int Y_POS_LOGOUT_ANNOUNCEMENT = 60;
    private static final int Y_POS_USERNAME = 20;
    private static final int X_POS_USERNAME = 2093;
    private static final int MIN_WIDTH_USERNAME = 715;
    private static final int LOG_BUTTON_WIDTH = 715;
    private static final int LOG_BUTTON_HEIGHT = 272;
    private static final int SIGNUP_Y_POS = 40;
    private static final int LOGIN_Y_POS = 340;
    private static final int LOGOUT_Y_POS = 130;
    private static final int AUDIO_X_POS = 1550;
    private static final int AUDIO_Y_POS = 1800;
    private static final int MAIN_BUTTON_WIDTH = 1271;
    private static final int MAIN_BUTTON_HEIGHT = 444;
    private static final int MAIN_BUTTON_X_POS = 145;
    private static final int STORY_Y_POS = 600;
    private static final int TUTORIAL_Y_POS = 1080;
    private static final int QUICK_Y_POS = 1560;
    private static final int QUIT_BUTTON_WIDTH = 717;
    private static final int QUIT_BUTTON_HEIGHT = 272;
    private static final int QUIT_X_POS = 2093;
    private static final int QUIT_Y_POS = 1775;
    private static final double BUTTON_NEW_SIZE_ON_HOVER_RATIO = 1.05;

    private final Pane fixedRoot = new Pane();

    private final MainMenuViewModel mainMenuViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final AuthOverlayFactories authOverlayFactories;
    private final Runnable onLogout;

    private final Label statusLabel = new Label();
    private final PauseTransition statusBannerTimer =
            new PauseTransition(Duration.seconds(15));
    private final Label usernameLabel = new Label();
    private ImageView signupButton;
    private ImageView loginButton;
    private ImageView logoutButton;

    private final TutorialAndStoryModeStartUpController
            tutorialAndStoryModeStartUpController;
    private final BrowseRoomsController browseRoomsController;

    private AbstractModalOverlay currentOverlay;

    /**
     * Creates the main menu view.
     *
     * @param mainMenuViewModel view model containing main menu state
     * @param loggedInViewModel view model containing the current login state
     * @param authOverlayFactories factories used to create the login and sign-up overlays
     * @param onLogout action to perform when the user logs out
     * @param audioControlView controls for sound effects and background music
     * @param tutorialAndStoryModeStartUpController controller for tutorial and
     *                                             story mode startup
     * @param browseRoomsController controller for browsing quick-play rooms
     */
    public MainMenuView(
            MainMenuViewModel mainMenuViewModel,
            LoggedInViewModel loggedInViewModel,
            AuthOverlayFactories authOverlayFactories,
            Runnable onLogout,
            AudioControlView audioControlView,
            TutorialAndStoryModeStartUpController
                    tutorialAndStoryModeStartUpController,
            BrowseRoomsController browseRoomsController) {

        this.mainMenuViewModel = mainMenuViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.authOverlayFactories = authOverlayFactories;
        this.onLogout = onLogout;
        this.tutorialAndStoryModeStartUpController =
                tutorialAndStoryModeStartUpController;
        this.browseRoomsController = browseRoomsController;

        initializeViewModels();
        initializeRoot();
        initializeLabels();
        initializeButtons(audioControlView);
        initializeScaling();

        updateAuthSection();
    }

    /**
     * Registers this view as a listener for relevant view model changes.
     */
    private void initializeViewModels() {
        mainMenuViewModel.addPropertyChangeListener(this);
        loggedInViewModel.addPropertyChangeListener(this);
    }

    /**
     * Initializes the fixed-size root pane and its background image.
     */
    private void initializeRoot() {
        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView background = new ImageView(
                loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        background.setFitWidth(DESIGN_WIDTH);
        background.setFitHeight(DESIGN_HEIGHT);

        fixedRoot.getChildren().add(background);
    }

    /**
     * Configures the status and username labels.
     */
    private void initializeLabels() {
        initializeStatusLabel();
        initializeUsernameLabel();
    }

    /**
     * Configures the status message banner.
     */
    private void initializeStatusLabel() {
        statusLabel.setStyle(
                "-fx-font-size: 64px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #2e7d32; "
                        + "-fx-background-color: white; "
                        + "-fx-background-radius: 16; "
                        + "-fx-padding: 24 48;");

        statusLabel.setLayoutY(Y_POS_LOGOUT_ANNOUNCEMENT);
        statusLabel.setCursor(Cursor.HAND);
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        statusLabel.layoutXProperty().bind(
                statusLabel.widthProperty()
                        .negate()
                        .divide(2)
                        .add(DESIGN_WIDTH / 2));

        statusLabel.setOnMouseClicked(event -> dismissStatusBanner());
        statusBannerTimer.setOnFinished(event -> dismissStatusBanner());
    }

    /**
     * Configures the username label.
     */
    private void initializeUsernameLabel() {
        usernameLabel.setStyle(
                "-fx-font-size: 60px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: #2e7d32;");

        usernameLabel.setLayoutX(X_POS_USERNAME);
        usernameLabel.setLayoutY(Y_POS_USERNAME);
        usernameLabel.setMinWidth(MIN_WIDTH_USERNAME);
        usernameLabel.setAlignment(Pos.CENTER_RIGHT);
    }

    /**
     * Creates and positions the main menu buttons and audio controls.
     *
     * @param audioControlView the audio controls displayed on the menu
     */
    private void initializeButtons(AudioControlView audioControlView) {
        signupButton = makeButton(
                "/images/ui/buttons/SignupButton.png",
                LOG_BUTTON_WIDTH, LOG_BUTTON_HEIGHT, X_POS_USERNAME, SIGNUP_Y_POS, this::onSignUp);

        loginButton = makeButton(
                "/images/ui/buttons/LoginButton.png",
                LOG_BUTTON_WIDTH, LOG_BUTTON_HEIGHT, X_POS_USERNAME, LOGIN_Y_POS, this::onLogin);

        logoutButton = makeButton(
                "/images/ui/buttons/LogoutButton.png",
                LOG_BUTTON_WIDTH, LOG_BUTTON_HEIGHT, X_POS_USERNAME, LOGOUT_Y_POS, onLogout);

        audioControlView.setLayoutX(AUDIO_X_POS);
        audioControlView.setLayoutY(AUDIO_Y_POS);

        fixedRoot.getChildren().addAll(
                statusLabel,
                usernameLabel,
                signupButton,
                loginButton,
                logoutButton,
                createStoryButton(),
                createTutorialButton(),
                createQuickButton(),
                createQuitButton(),
                audioControlView);

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);
    }

    /**
     * Creates the story mode button.
     *
     * @return the story mode button
     */
    private ImageView createStoryButton() {
        return makeButton(
                "/images/ui/buttons/StoryButton.png",
                MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT, MAIN_BUTTON_X_POS, STORY_Y_POS,
                () -> tutorialAndStoryModeStartUpController.execute("STORY"));
    }

    /**
     * Creates the tutorial button.
     *
     * @return the tutorial button
     */
    private ImageView createTutorialButton() {
        return makeButton(
                "/images/ui/buttons/TutorialButton.png",
                MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT, MAIN_BUTTON_X_POS, TUTORIAL_Y_POS,
                () -> tutorialAndStoryModeStartUpController.execute("STORY"));
    }

    /**
     * Creates the quick-play button.
     *
     * @return the quick-play button
     */
    private ImageView createQuickButton() {
        return makeButton(
                "/images/ui/buttons/QuickButton.png",
                MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT, MAIN_BUTTON_X_POS, QUICK_Y_POS,
                browseRoomsController::execute);
    }

    /**
     * Creates the quit button.
     *
     * @return the quit button
     */
    private ImageView createQuitButton() {
        return makeButton(
                "/images/ui/buttons/QuitButton.png",
                QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT, QUIT_X_POS, QUIT_Y_POS,
                Platform::exit);
    }

    /**
     * Configures listeners that rescale the menu when the window changes size.
     */
    private void initializeScaling() {
        widthProperty().addListener(
                (observable, oldValue, newValue) -> rescale());
        heightProperty().addListener(
                (observable, oldValue, newValue) -> rescale());
    }

    /**
     * Updates the view when a property change occurs.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateStatusBanner();
        updateAuthSection();
    }

    /**
     * Updates the status banner using the current status message.
     */
    private void updateStatusBanner() {
        String message = mainMenuViewModel.getState().getStatusMessage();
        boolean show = message != null && !message.isEmpty();

        if (show) {
            statusLabel.setText(message);
            statusLabel.setVisible(true);
            statusLabel.setManaged(true);
            statusBannerTimer.stop();
            statusBannerTimer.playFromStart();
        }
        else {
            dismissStatusBanner();
        }
    }

    /**
     * Hides the status banner and stops its dismissal timer.
     */
    private void dismissStatusBanner() {
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);
        statusBannerTimer.stop();
    }

    /**
     * Updates the authentication controls based on whether a user is logged
     * in.
     */
    private void updateAuthSection() {
        boolean loggedIn = loggedInViewModel.getState().isLoggedIn();

        signupButton.setVisible(!loggedIn);
        signupButton.setManaged(!loggedIn);
        loginButton.setVisible(!loggedIn);
        loginButton.setManaged(!loggedIn);

        logoutButton.setVisible(loggedIn);
        logoutButton.setManaged(loggedIn);
        usernameLabel.setVisible(loggedIn);
        usernameLabel.setManaged(loggedIn);
        usernameLabel.setText(
                "Username: "
                        + loggedInViewModel.getState().getUsername());
    }

    /**
     * Creates an image-based button with hover and click behaviour.
     *
     * @param resourcePath path to the button image
     * @param imgWidth width of the button image
     * @param imgHeight height of the button image
     * @param x_pos horizontal position of the button
     * @param y_pos vertical position of the button
     * @param onClick action performed when the button is clicked
     * @return the configured image button
     */
    private ImageView makeButton(
            String resourcePath,
            double imgWidth,
            double imgHeight,
            double x_pos,
            double y_pos,
            Runnable onClick) {

        ImageView button = new ImageView(loadImage(resourcePath));
        button.setFitWidth(imgWidth);
        button.setFitHeight(imgHeight);
        button.setLayoutX(x_pos);
        button.setLayoutY(y_pos);

        button.setPickOnBounds(true);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(event -> onClick.run());

        button.setOnMouseEntered(event -> {
            button.setScaleX(BUTTON_NEW_SIZE_ON_HOVER_RATIO);
            button.setScaleY(BUTTON_NEW_SIZE_ON_HOVER_RATIO);
        });

        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        return button;
    }

    /**
     * Loads an image from the application's resources.
     *
     * @param resourcePath path to the image resource
     * @return the loaded image
     * @throws IllegalArgumentException if the resource cannot be found
     */
    private Image loadImage(String resourcePath) {
        InputStream stream =
                getClass().getResourceAsStream(resourcePath);

        if (stream == null) {
            throw new IllegalArgumentException(
                    "Resource not found: " + resourcePath);
        }

        return new Image(stream);
    }

    /**
     * Scales the fixed-size menu to fit the available window while preserving
     * its aspect ratio.
     */
    private void rescale() {
        double scale = Math.min(
                getWidth() / DESIGN_WIDTH,
                getHeight() / DESIGN_HEIGHT);

        fixedRoot.setScaleX(scale);
        fixedRoot.setScaleY(scale);
    }

    /**
     * Opens the sign-up overlay.
     */
    private void onSignUp() {
        showOverlay(authOverlayFactories.signupOverlayFactory().create(this::closeOverlay));
    }

    /**
     * Opens the login overlay.
     */
    private void onLogin() {
        showOverlay(authOverlayFactories.loginOverlayFactory().create(this::closeOverlay));
    }

    /**
     * Closes the current sign-up overlay and opens the login overlay.
     */
    public void switchFromSignupToLogin() {
        closeOverlay();
        showOverlay(authOverlayFactories.loginOverlayFactory().create(this::closeOverlay));
    }

    /**
     * Displays the specified modal overlay.
     *
     * @param overlay the overlay to display
     */
    private void showOverlay(AbstractModalOverlay overlay) {
        if (currentOverlay != null) {
            getChildren().remove(currentOverlay);
        }

        currentOverlay = overlay;
        getChildren().add(currentOverlay);
        currentOverlay.requestFocus();
    }

    /**
     * Closes the currently displayed modal overlay, if one exists.
     */
    private void closeOverlay() {
        if (currentOverlay != null) {
            getChildren().remove(currentOverlay);
            currentOverlay = null;
        }
    }
}
