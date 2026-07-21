package view.mainmenu;

import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginViewModel;
import interface_adapter.User.Signup.SignupController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.user.LoginOverlay;
import view.user.SignupOverlay;
import view.ViewManager;
import view.common.ModalOverlay;
import view.common.PlaceholderView;

public class MainMenuView extends StackPane {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();

    private final ViewManager viewManager;

    private final LoginController loginController;
    private final LoginViewModel loginViewModel;

    private final SignupController signupController;

    public MainMenuView(ViewManager viewManager, LoginController loginController, LoginViewModel loginViewModel,
                        SignupController signupController) {

        this.viewManager = viewManager;

        this.loginController = loginController;
        this.loginViewModel = loginViewModel;

        this.signupController = signupController;

        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);

        fixedRoot.getChildren().add(bg);

        fixedRoot.getChildren().addAll(
                makeButton("/images/ui/buttons/SignupButton.png", 715, 272, 2093,
                        40, this::onSignUp),
                makeButton("/images/ui/buttons/LoginButton.png", 715, 272, 2093,
                        340, this::onLogin),
                makeButton("/images/ui/buttons/StoryButton.png", 1271, 444, 145, 600,
                        () -> this.viewManager.show(new PlaceholderView("Story Line", this.viewManager, this))),
                makeButton("/images/ui/buttons/TutorialButton.png", 1271, 444, 145, 1080,
                        () -> this.viewManager.show(new PlaceholderView("Tutorial", this.viewManager, this))),
                makeButton("/images/ui/buttons/QuickButton.png", 1271, 444, 145, 1560,
                        () -> this.viewManager.show(new PlaceholderView("Quick Game", this.viewManager, this))),
                makeButton("/images/ui/buttons/QuitButton.png", 717, 272, 2093, 1775,
                        Platform::exit)
        );

        getChildren().add(fixedRoot);
        setAlignment(Pos.CENTER);

        widthProperty().addListener((o, ov, nv) -> rescale());
        heightProperty().addListener((o, ov, nv) -> rescale());
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
        showOverlay(new SignupOverlay(this::closeOverlay, signupController));
    }

    private void onLogin() {
        showOverlay(new LoginOverlay(this::closeOverlay, loginController, loginViewModel));
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
}