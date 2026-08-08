package view.user;

import interface_adapter.User.Signup.SignupController;
import interface_adapter.User.Signup.SignupViewModel;
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

/**
 * Modal overlay that provides the user interface for creating a new account.
 *
 * <p>The overlay collects a username, password, and repeated password and
 * delegates the sign-up operation to the SignupController. Validation errors
 * are displayed using the associated SignupViewModel.</p>
 */
public class SignupOverlay extends AbstractModalOverlay {

    private static final double SPACING_H = 20;
    private static final double SPACING_V = 16;
    private static final int PADDING = 24;
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final PasswordField repeatedPasswordField = new PasswordField();
    private final Label errorLabel = new Label();

    private final SignupController signupController;
    private final SignupViewModel signupViewModel;

    /**
     * Creates a sign-up overlay.
     *
     * @param onClose the action to perform when the overlay is closed
     * @param signupController the controller responsible for sign-up actions
     * @param signupViewModel the view model containing sign-up state and errors
     */
    public SignupOverlay(
            Runnable onClose,
            SignupController signupController,
            SignupViewModel signupViewModel) {
        super(onClose);
        this.signupController = signupController;
        this.signupViewModel = signupViewModel;
        initialize();
    }

    /**
     * Builds the modal box containing the sign-up form.
     *
     * @return the VBox containing the sign-up form and controls
     */
    @Override
    protected VBox buildModalBox() {
        Label title = new Label("SIGN-UP");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button closeButton = new Button("\u00D7");
        closeButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: transparent; "
                        + "-fx-text-fill: white; -fx-cursor: hand;");
        closeButton.setOnAction(evt -> onClose.run());

        HBox header = new HBox(title, closeButton);
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title, Priority.ALWAYS);

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        repeatedPasswordField.setPromptText("Confirm Password");

        errorLabel.setStyle("-fx-text-fill: #ffb3b3; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(evt -> onClose.run());

        Button confirmButton = new Button("CONFIRM");
        confirmButton.setOnAction(evt -> {
            signupController.execute(
                    usernameField.getText(),
                    passwordField.getText(),
                    repeatedPasswordField.getText()
            );
            updateView();
        });

        HBox buttonRow = new HBox(SPACING_H, cancelButton, confirmButton);
        buttonRow.setAlignment(Pos.CENTER);

        Button switchToLoginLink = new Button("Already have an account? Log in");
        switchToLoginLink.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; "
                        + "-fx-underline: true; -fx-cursor: hand;");
        switchToLoginLink.setOnAction(evt -> signupController.switchToLoginView());

        VBox box = new VBox(
                SPACING_V,
                header,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Confirm Password:"), repeatedPasswordField,
                errorLabel, buttonRow
        );
        box.setPadding(new Insets(PADDING));
        box.setMaxWidth(MAX_WIDTH);
        box.setMaxHeight(MAX_HEIGHT);
        box.setStyle("-fx-background-color: #a6389e; -fx-background-radius: 16;");
        return box;
    }

    /**
     * Updates the overlay based on the current sign-up state.
     *
     * <p>If an error is present, the error message is displayed. If no error
     * is present, the sign-up is considered successful and the overlay is
     * closed.</p>
     */
    public void updateView() {
        String error = signupViewModel.getState().getErrorMessage();

        if (!error.isEmpty()) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
        else {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
            onClose.run();
        }
    }
}
