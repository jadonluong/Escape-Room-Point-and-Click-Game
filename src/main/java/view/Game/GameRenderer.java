package view.Game;

import application.use_cases.GamePlay.ObjectsInfo;
import interface_adapter.GamePlay.InGameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class GameRenderer {

    private final Pane gamePane;
    private final Map<String, ImageView> renderedObjects = new HashMap<>();

    public GameRenderer(Pane objectPane) {
        this.gamePane = objectPane;
    }

    public void renderOne(InGameState state, String id){

        ObjectsInfo targetInfo =
                state.getObjectsToDisplay().get(id);

        if (targetInfo != null) {

            ImageView old =
                    renderedObjects.get(id);

            if (old != null) {
                gamePane.getChildren().remove(old);
            }

            render(id, targetInfo);
        }

    }

    public void renderAll(InGameState state) {
        clear();

        Map<String, ObjectsInfo> objects =
                state.getObjectsToDisplay();

        for (Map.Entry<String, ObjectsInfo> entry : objects.entrySet()) {
            render(entry.getKey(), entry.getValue());
        }
    }

    private void render(String id, ObjectsInfo info) {

        Image image = new Image(info.imgPath());
        ImageView imageView = new ImageView(image);

        imageView.setLayoutX(
                info.position().x()
        );
        imageView.setLayoutY(
                info.position().y()
        );

        renderedObjects.put(id, imageView);
        gamePane.getChildren().add(imageView);
    }


    private void clear() {
        gamePane.getChildren().clear();
        renderedObjects.clear();
    }
}