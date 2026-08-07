package view.user;

import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import view.common.AbstractModalOverlay;

public class LoginOverlay extends AbstractModalOverlay {

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label errorLabel = new Label();

    private final LoginController controller;
    LoginViewModel loginViewModel;

    public LoginOverlay(Runnable onClose, LoginController controller, LoginViewModel loginViewModel) {
        super(onClose);
        this.controller = controller;
        this.loginViewModel = loginViewModel;
        initialize();
    }

    @Override
    protected VBox buildModalBox() {
        Label title = new Label("LOG-IN");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button closeButton = new Button("\u00D7");
        closeButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;");
        closeButton.setOnAction(e -> onClose.run());

        HBox header = new HBox(title, closeButton);
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title, Priority.ALWAYS);

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        errorLabel.setStyle("-fx-text-fill: #ffb3b3; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e -> onClose.run());

        Button confirmButton = new Button("CONFIRM");
        confirmButton.setOnAction(e -> {
            controller.execute(
                    usernameField.getText(),
                    passwordField.getText()
            );

            updateView();
        });

        HBox buttonRow = new HBox(20, cancelButton, confirmButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox box = new VBox(16, header,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                errorLabel, buttonRow);
        box.setPadding(new Insets(24));
        box.setMaxWidth(400);
        box.setMaxHeight(300);
        box.setStyle("-fx-background-color: #a6389e; -fx-background-radius: 16;");
        return box;
    }

    public void updateView() {
        String error = loginViewModel.getState().getErrorMessage();
        if (!error.isEmpty()) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        } else {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
            onClose.run(); // success - close the modal
        }
    }
}
