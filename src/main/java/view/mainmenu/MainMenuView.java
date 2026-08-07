package view.mainmenu;

import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsController;
import interface_adapter.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpController;
import interface_adapter.User.LoggedIn.LoggedInViewModel;
import interface_adapter.User.MainMenu.MainMenuViewModel;
import interface_adapter.ViewManagerModel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import view.common.AudioControlView;
import view.common.AbstractModalOverlay;
import view.common.OverlayFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();

    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final OverlayFactory loginOverlayFactory;
    private final OverlayFactory signupOverlayFactory;
    private final Runnable onLogout;

    private final Label statusLabel = new Label();
    private final PauseTransition statusBannerTimer = new PauseTransition(Duration.seconds(15));
    private final Label usernameLabel = new Label();
    private final ImageView signupButton;
    private final ImageView loginButton;
    private final ImageView logoutButton;

    private final TutorialAndStoryModeStartUpController tutorialAndStoryModeStartUpController;
    private final BrowseRoomsController browseRoomsController;

    public MainMenuView(ViewManagerModel viewManagerModel,
                        MainMenuViewModel mainMenuViewModel,
                        LoggedInViewModel loggedInViewModel,
                        OverlayFactory loginOverlayFactory,
                        OverlayFactory signupOverlayFactory,
                        Runnable onLogout,
                        AudioControlView audioControlView,
                        TutorialAndStoryModeStartUpController tutorialAndStoryModeStartUpController,
                        BrowseRoomsController browseRoomsController) {

        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginOverlayFactory = loginOverlayFactory;
        this.signupOverlayFactory = signupOverlayFactory;
        this.onLogout = onLogout;
        this.tutorialAndStoryModeStartUpController = tutorialAndStoryModeStartUpController;
        this.browseRoomsController = browseRoomsController;

        mainMenuViewModel.addPropertyChangeListener(this);
        loggedInViewModel.addPropertyChangeListener(this);

        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        fixedRoot.getChildren().add(bg);

        statusLabel.setStyle(
                "-fx-font-size: 64px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;" +
                        "-fx-background-color: white; -fx-background-radius: 16; -fx-padding: 24 48;"
        );
        statusLabel.setLayoutY(60);
        statusLabel.setCursor(Cursor.HAND);
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        // Keeps it horizontally centered regardless of message length, since width
        // isn't known until the label actually renders its text.
        statusLabel.layoutXProperty().bind(
                statusLabel.widthProperty().negate().divide(2).add(DESIGN_WIDTH / 2)
        );

        statusLabel.setOnMouseClicked(e -> dismissStatusBanner());
        statusBannerTimer.setOnFinished(e -> dismissStatusBanner());

        usernameLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        usernameLabel.setLayoutX(2093);
        usernameLabel.setLayoutY(20);
        usernameLabel.setMinWidth(715);
        usernameLabel.setAlignment(Pos.CENTER_RIGHT);

        signupButton = makeButton("/images/ui/buttons/SignupButton.png", 715, 272, 2093, 40, this::onSignUp);
        loginButton = makeButton("/images/ui/buttons/LoginButton.png", 715, 272, 2093, 340, this::onLogin);
        logoutButton = makeButton("/images/ui/buttons/LogoutButton.png", 715, 235, 2093, 130, this.onLogout);

        audioControlView.setLayoutX(1550);
        audioControlView.setLayoutY(1800);

        fixedRoot.getChildren().addAll(
                statusLabel, usernameLabel,
                signupButton, loginButton, logoutButton,
                makeButton("/images/ui/buttons/StoryButton.png", 1271, 444, 145, 600, () -> tutorialAndStoryModeStartUpController.execute("STORY")),
                makeButton("/images/ui/buttons/TutorialButton.png", 1271, 444, 145, 1080, () -> tutorialAndStoryModeStartUpController.execute("STORY")),
                makeButton("/images/ui/buttons/QuickButton.png", 1271, 444, 145, 1560, browseRoomsController::execute),
                makeButton("/images/ui/buttons/QuitButton.png", 717, 272, 2093, 1775, Platform::exit),
                audioControlView
        );

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());

        updateAuthSection(); // set correct initial visibility before first paint
    }

    private void navigateTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateStatusBanner();
        updateAuthSection();
    }

    private void updateStatusBanner() {
        String message = mainMenuViewModel.getState().getStatusMessage();
        boolean show = message != null && !message.isEmpty();

        if (show) {
            statusLabel.setText(message);
            statusLabel.setVisible(true);
            statusLabel.setManaged(true);
            statusBannerTimer.stop();
            statusBannerTimer.playFromStart();
        } else {
            dismissStatusBanner();
        }
    }

    private void dismissStatusBanner() {
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);
        statusBannerTimer.stop();
    }

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
        usernameLabel.setText("Username: " + loggedInViewModel.getState().getUsername());
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

    private AbstractModalOverlay currentOverlay;

    private void onSignUp() {
        showOverlay(signupOverlayFactory.create(this::closeOverlay));
    }

    private void onLogin() {
        showOverlay(loginOverlayFactory.create(this::closeOverlay));
    }

    public void switchFromSignupToLogin() {
        closeOverlay();
        showOverlay(loginOverlayFactory.create(this::closeOverlay));
    }

    private void showOverlay(AbstractModalOverlay overlay) {
        if (currentOverlay != null) getChildren().remove(currentOverlay);
        currentOverlay = overlay;
        getChildren().add(currentOverlay);
        currentOverlay.requestFocus();
    }

    private void closeOverlay() {
        if (currentOverlay != null) {
            getChildren().remove(currentOverlay);
            currentOverlay = null;
        }
    }
}