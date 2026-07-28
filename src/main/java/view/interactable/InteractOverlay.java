package view.interactable;

import interface_adapter.Interactable.Interact.InteractState;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import view.common.ModalOverlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InteractOverlay extends ModalOverlay implements ActionListener, PropertyChangeListener {
    private final InteractViewModel interactViewModel;

    private Label messageLabel = new Label();
    private Label subtitleLabel = new Label("Click anywhere outside the box to dismiss this message.");

    public InteractOverlay(InteractViewModel interactViewModel, ViewManagerModel viewManagerModel) {
        super(() -> { // When InteractOverlay closes
            InteractState state = interactViewModel.getState();
            String returnToView = state.getReturnToView();

            state.setSuccessMessage(null);
            state.setErrorMessage(null);
            state.setReturnToView(null);

            if (returnToView != null) { // In case I forget to set this...
                viewManagerModel.setState(returnToView);
            } else {
                viewManagerModel.setState("Room");
            }
            viewManagerModel.firePropertyChanged();
        });

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
        // Nothing needs to be done.
    }

    @Override
    protected VBox buildModalBox() {
        InteractState interactState = interactViewModel.getState();

        VBox modalBox = new VBox(15);
        modalBox.setAlignment(Pos.CENTER);
        modalBox.setPadding(new Insets(40, 50, 40, 50));
        modalBox.setStyle(
                "-fx-background-color: #2a2a2a; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 3; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );
        modalBox.setPrefSize(343, 343); // I just like this number...
        modalBox.setMaxSize(343, 343);

        // Message Label
        if (interactState.getSuccessMessage() != null) { // Recall: exactly one of successMessage & errorMessage !null
            messageLabel.setText(interactState.getSuccessMessage());
            messageLabel.setTextFill(Color.web("#80EF80"));
        } else {
            messageLabel.setText(interactState.getErrorMessage());
            messageLabel.setTextFill(Color.web("#E54C38"));
        }
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        // -------------

        // Subtitle Label
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        subtitleLabel.setTextFill(Color.WHITE);
        subtitleLabel.setWrapText(true);
        subtitleLabel.setAlignment(Pos.CENTER);
        // --------------

        modalBox.getChildren().addAll(messageLabel, subtitleLabel);
        return modalBox;
    }
}
