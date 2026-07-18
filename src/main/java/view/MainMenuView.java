package view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainMenuView extends StackPane {

    private static final double DESIGN_WIDTH = 2907;
    private static final double DESIGN_HEIGHT = 2040;

    private final Pane fixedRoot = new Pane();
    private final ViewManager viewManager;

    public MainMenuView(ViewManager viewManager) {
        this.viewManager = viewManager;

        fixedRoot.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMinSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        fixedRoot.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        ImageView bg = new ImageView(loadImage("/images/ui/backgrounds/MainMenuUnloggedBG.png"));
        bg.setFitWidth(DESIGN_WIDTH);
        bg.setFitHeight(DESIGN_HEIGHT);

        fixedRoot.getChildren().add(bg);

        fixedRoot.getChildren().addAll(
                makeButton("/images/ui/buttons/LoginSigninButton.png", 715, 566, 2093, 40,
                        () -> viewManager.show(new PlaceholderView("Login / Sign Up", viewManager, this))),
                makeButton("/images/ui/buttons/StoryButton.png", 1271, 444, 145, 600,
                        () -> viewManager.show(new PlaceholderView("Story Line", viewManager, this))),
                makeButton("/images/ui/buttons/TutorialButton.png", 1271, 444, 145, 1080,
                        () -> viewManager.show(new PlaceholderView("Tutorial", viewManager, this))),
                makeButton("/images/ui/buttons/QuickButton.png", 1271, 444, 145, 1560,
                        () -> viewManager.show(new PlaceholderView("Quick Game", viewManager, this))),
                makeButton("/images/ui/buttons/QuitButton.png", 717, 272, 2093, 1775,
                        () -> javafx.application.Platform.exit())
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

        button.setOnMouseEntered(e -> { button.setScaleX(1.05); button.setScaleY(1.05); });
        button.setOnMouseExited(e -> { button.setScaleX(1.0); button.setScaleY(1.0); });

//        javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow(0);
//        button.setEffect(glow);
//        button.setOnMouseEntered(e -> glow.setLevel(0.4));
//        button.setOnMouseExited(e -> glow.setLevel(0));

        return button;
    }

    private Image loadImage(String resourcePath) {
        return new Image(getClass().getResourceAsStream(resourcePath));
    }

    private void rescale() {
        double scale = Math.min(getWidth() / DESIGN_WIDTH, getHeight() / DESIGN_HEIGHT);
        fixedRoot.setScaleX(scale);
        fixedRoot.setScaleY(scale);
    }
}
