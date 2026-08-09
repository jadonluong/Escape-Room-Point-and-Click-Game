package view.interactable;

import interface_adapter.interactable.interact.InteractState;
import interface_adapter.interactable.interact.InteractViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import view.ViewManager;
import view.common.AbstractModalOverlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InteractOverlay extends AbstractModalOverlay implements ActionListener, PropertyChangeListener {
    private final InteractViewModel interactViewModel;

    private Label messageLabel = new Label();
    private Label subtitleLabel = new Label("Click anywhere outside the box to dismiss this message.");

    public InteractOverlay(InteractViewModel interactViewModel, ViewManager viewManager) {
        super(() -> viewManager.hideOverlay("Interact")); // When the overlay closes.

        this.interactViewModel = interactViewModel;
        this.interactViewModel.addPropertyChangeListener(this);

        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        InteractState state = interactViewModel.getState();
        if (state.getSuccessMessage() != null) {
            messageLabel.setText(state.getSuccessMessage());
            messageLabel.setTextFill(Color.web("#80EF80"));
        } else if (state.getErrorMessage() != null) {
            messageLabel.setText(state.getErrorMessage());
            messageLabel.setTextFill(Color.web("#E54C38"));
        } else { // In case there's no successMessage or errorMessage.
            this.onClose.run();
        }
    }

    @Override
    protected VBox buildModalBox() {
        VBox modalBox = new VBox(10);
        modalBox.setAlignment(Pos.CENTER);
        modalBox.setPadding(new Insets(40, 30, 30, 30));
        modalBox.setStyle(
                "-fx-background-color: #2a2a2a; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 3; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );
        modalBox.setPrefSize(450, 450);
        modalBox.setMaxSize(450, 450);

        // Message Label
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        // -------------

        Region spacer = new Region(); // So that Subtitle Label is at the bottom of the overlay
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Subtitle Label
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitleLabel.setTextFill(Color.WHITE);
        subtitleLabel.setAlignment(Pos.CENTER);
        // --------------

        modalBox.getChildren().addAll(messageLabel, spacer, subtitleLabel);
        return modalBox;
    }
}
