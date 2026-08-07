package view.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class AbstractModalOverlay extends StackPane {

    private static final double OPACITY = 0.5;

    // Purposeful Violation of CSC207 Checks - classes that extend this one depend on it
    protected final Runnable onClose;

    protected AbstractModalOverlay(Runnable onClose) {
        this.onClose = onClose;

    }

    /**
     * Must be called by each subclass constructor, as its very last statement,
     * once all of that subclass's own fields have been initialized.
     */
    protected void initialize() {
        Region backdrop = new Region();
        backdrop.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 0, 0, OPACITY), CornerRadii.EMPTY, Insets.EMPTY)));
        backdrop.setOnMouseClicked(evt -> onClose.run());

        VBox modalBox = buildModalBox();
        modalBox.setOnMouseClicked(javafx.event.Event::consume);

        getChildren().addAll(backdrop, modalBox);
        setAlignment(modalBox, Pos.CENTER);

        setFocusTraversable(true);
        addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
            if (evt.getCode() == KeyCode.ESCAPE) {
                onClose.run();
            }
        });
    }

    protected abstract VBox buildModalBox();
}
