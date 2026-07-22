package view.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class ModalOverlay extends StackPane {

    protected final Runnable onClose;

    protected ModalOverlay(Runnable onClose) {
        this.onClose = onClose;

    }

    /**
     * Must be called by each subclass constructor, as its very last statement,
     * once all of that subclass's own fields have been initialized.
     */
    protected void initialize() {
        Region backdrop = new Region();
        backdrop.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        backdrop.setOnMouseClicked(e -> onClose.run());

        VBox modalBox = buildModalBox();
        modalBox.setOnMouseClicked(javafx.event.Event::consume);

        getChildren().addAll(backdrop, modalBox);
        setAlignment(modalBox, Pos.CENTER);

        setFocusTraversable(true);
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) onClose.run();
        });
    }

    protected abstract VBox buildModalBox();
}