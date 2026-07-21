package view.user;

import interface_adapter.User.Signup.SignupController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import view.common.ModalOverlay;



public class SignupOverlay extends ModalOverlay {

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final PasswordField repeatedPasswordField = new PasswordField();

    private final Label errorLabel = new Label();
    private final SignupController signupController;

    public SignupOverlay(Runnable onClose, SignupController signupController) {
        super(onClose);
        this.signupController = signupController;
    }

    @Override
    protected VBox buildModalBox() {
        Label title = new Label("SIGN-UP");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button closeButton = new Button("\u00D7");
        closeButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;");
        closeButton.setOnAction(e -> onClose.run());

        HBox header = new HBox(title, closeButton);
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title, Priority.ALWAYS);

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        passwordField.setPromptText("Confirm Password");

        errorLabel.setStyle("-fx-text-fill: #ffb3b3; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e -> onClose.run());

        Button confirmButton = new Button("CONFIRM");
        confirmButton.setOnAction(e ->
                signupController.execute(usernameField.getText(), passwordField.getText()), repeatedPasswordField.getText());

        HBox buttonRow = new HBox(20, cancelButton, confirmButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox box = new VBox(16, header,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Confirm Password:", repeatedPasswordField),
                errorLabel, buttonRow);
        box.setPadding(new Insets(24));
        box.setMaxWidth(400);
        box.setStyle("-fx-background-color: #a6389e; -fx-background-radius: 16;");
        return box;
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}
