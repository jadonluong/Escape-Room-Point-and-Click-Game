package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
    private static final double INITIAL_WIDTH = 1280;
    private static final double INITIAL_HEIGHT = 720;

    private final Stage stage;

    public ViewManager(Stage stage) {
        this.stage = stage;
    }

    public void show(Parent view) {
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(view, INITIAL_WIDTH, INITIAL_HEIGHT);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
        } else {
            scene.setRoot(view);
        }
        stage.show();
    }
}
