package view.Hint;

import interface_adapter.Hint.GetHintState;
import interface_adapter.Hint.GetHintViewModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import view.ViewManager;
import view.common.ModalOverlay;

public class HintOverlay extends ModalOverlay implements PropertyChangeListener{

    private final GetHintViewModel getHintViewModel;
    private Label messageLabel;

    public HintOverlay(GetHintViewModel getHintViewModel, ViewManager viewManager) {
        super(() -> viewManager.hideOverlay("get hint"));
        this.getHintViewModel = getHintViewModel;

        // Register listener for view model updates
        this.getHintViewModel.addPropertyChangeListener(this);

        // Required call per ModalOverlay contract
        initialize();

        // Load initial state
        updateFromState(getHintViewModel.getState());
    }

    @Override
    protected void initialize() {
        // Build the floating card container
        VBox modalBox = buildModalBox();
        modalBox.setOnMouseClicked(javafx.event.Event::consume);

        // Add ONLY the modal card to the layout — NO dark backdrop
        getChildren().add(modalBox);

        // Allow mouse clicks outside the modal card to PASS THROUGH to the game below
        this.setPickOnBounds(false);

        // Position card at TOP_CENTER of the screen
        setAlignment(modalBox, Pos.TOP_CENTER);

        // Add a top margin (20px) so the card doesn't stick directly to the top window edge
        StackPane.setMargin(modalBox, new Insets(10, 0, 0, 0));

        // Retain ESC key shortcut handling
        setFocusTraversable(true);
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                onClose.run();
            }
        });
    }

    @Override
    protected VBox buildModalBox() {
        // Message label setup
        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: #000000;"
        );

        // Cross icon close button (no background, no border)
        Button closeButton = new Button("✕");
        closeButton.setDefaultButton(true);
        closeButton.setOnAction(e ->{
            javafx.scene.Scene currentScene = getScene();

            onClose.run();

            if (currentScene != null && currentScene.getRoot() != null) {
                currentScene.getRoot().requestFocus();
            }}
        );

        String defaultCrossStyle =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: #d32f2f;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0 4 0 4;" +
                        "-fx-cursor: hand;";

        String hoverCrossStyle =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: #b71c1c;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0 4 0 4;" +
                        "-fx-cursor: hand;";

        closeButton.setStyle(defaultCrossStyle);
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(hoverCrossStyle));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(defaultCrossStyle));

        // Position the cross icon in the top-right corner of the white card
        StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

        // Content box holding the message
        VBox contentBox = new VBox(messageLabel);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(10, 20, 10, 10));

        // Main modal container
        VBox modalBox = new VBox();
        StackPane cardLayout = new StackPane(contentBox, closeButton);
        modalBox.getChildren().add(cardLayout);

        modalBox.setAlignment(Pos.CENTER);
        modalBox.setPadding(new Insets(8, 12, 8, 12));
        modalBox.setMaxWidth(450);
        modalBox.setMaxHeight(85);

        // Solid white card with crisp border
        modalBox.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-border-width: 1px;"
        );

        return modalBox;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof GetHintState state) {
            updateFromState(state);
        }
    }

    // The error message is not shown to the user.
    private void updateFromState(GetHintState state) {
        if (state.getSuccessMessage() != null) {
            messageLabel.setText(state.getSuccessMessage());
        }
    }
}
