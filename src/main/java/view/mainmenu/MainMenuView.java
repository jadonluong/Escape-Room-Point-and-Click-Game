package view.mainmenu;

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
import view.common.AudioControlView;
import view.common.ModalOverlay;
import view.common.OverlayFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends StackPane implements PropertyChangeListener {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();

    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final OverlayFactory loginOverlayFactory;
    private final OverlayFactory signupOverlayFactory;

    private final Label statusLabel = new Label();

    public MainMenuView(ViewManagerModel viewManagerModel,
                        MainMenuViewModel mainMenuViewModel,
                        OverlayFactory loginOverlayFactory,
                        OverlayFactory signupOverlayFactory,
                        AudioControlView audioControlView) {

        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginOverlayFactory = loginOverlayFactory;
        this.signupOverlayFactory = signupOverlayFactory;

        mainMenuViewModel.addPropertyChangeListener(this);

        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);
        fixedRoot.getChildren().add(bg);

        statusLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e7d32; -fx-background-color: white; -fx-padding: 8 16;");
        statusLabel.setLayoutX(145);
        statusLabel.setLayoutY(120);
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        audioControlView.setLayoutX(2280);
        audioControlView.setLayoutY(1850);

        fixedRoot.getChildren().addAll(
                statusLabel,
                makeButton("/images/ui/buttons/SignupButton.png", 715, 272, 2093, 40, this::onSignUp),
                makeButton("/images/ui/buttons/LoginButton.png", 715, 272, 2093, 340, this::onLogin),
                makeButton("/images/ui/buttons/StoryButton.png", 1271, 444, 145, 600, () -> navigateTo("story")),
                makeButton("/images/ui/buttons/TutorialButton.png", 1271, 444, 145, 1080, () -> navigateTo("tutorial")),
                makeButton("/images/ui/buttons/QuickButton.png", 1271, 444, 145, 1560, () -> navigateTo("quick game")),
                makeButton("/images/ui/buttons/QuitButton.png", 717, 272, 2093, 1775, Platform::exit),
                audioControlView
        );

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());
    }

    private void navigateTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String message = mainMenuViewModel.getState().getStatusMessage();
        if (message != null && !message.isEmpty()) {
            statusLabel.setText(message);
            statusLabel.setVisible(true);
            statusLabel.setManaged(true);
        } else {
            statusLabel.setVisible(false);
            statusLabel.setManaged(false);
        }
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

    private ModalOverlay currentOverlay;

    private void onSignUp() {
        showOverlay(signupOverlayFactory.create(this::closeOverlay));
    }

    private void onLogin() {
        showOverlay(loginOverlayFactory.create(this::closeOverlay));
    }

    private void showOverlay(ModalOverlay overlay) {
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

    public void switchFromSignupToLogin() {
        // TODO: add
    }
}