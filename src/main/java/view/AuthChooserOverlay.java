package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AuthChooserOverlay extends ModalOverlay {

    private final Runnable onSignUp;
    private final Runnable onLogIn;

    public AuthChooserOverlay(Runnable onClose, Runnable onSignUp, Runnable onLogIn) {
        super(onClose);
        this.onSignUp = onSignUp;
        this.onLogIn = onLogIn;
    }

    @Override
    protected VBox buildModalBox() {
        Label title = new Label("LOG-IN // SIGN-UP");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        ImageView signUpBtn = makeImageButton("/images/ui/buttons/SignupButton.png", onSignUp);
        ImageView logInBtn = makeImageButton("/images/ui/buttons/LoginButton.png", onLogIn);

        HBox buttonRow = new HBox(24, signUpBtn, logInBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox box = new VBox(24, title, buttonRow);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(32));
        box.setMaxWidth(500);
        box.setStyle("-fx-background-color: #a6389e; -fx-background-radius: 16;");
        return box;
    }

    private ImageView makeImageButton(String resourcePath, Runnable onClick) {
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(resourcePath)));
        // TODO: set real fitWidth/fitHeight once you share SignupButton/LoginButton dimensions
        iv.setPickOnBounds(false);
        iv.setCursor(Cursor.HAND);
        iv.setOnMouseClicked(e -> onClick.run());
        return iv;
    }
}
