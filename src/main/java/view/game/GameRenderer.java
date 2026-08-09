package view.game;

import application.use_cases.game_play.ObjectsInfo;
import interface_adapter.game_play.InGameState;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class GameRenderer {

    private final Pane gamePane;
    private final Map<String, ImageView> renderedObjects = new HashMap<>();

    private final ImgLoader imgLoader = new ImgLoader();

    private final GameObjectActionDispatcher actionDispatcher;

    public GameRenderer(Pane objectPane, GameObjectActionDispatcher actionDispatcher) {
        this.gamePane = objectPane;


        this.actionDispatcher = actionDispatcher;
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

        Runnable clickAction = actionDispatcher.getAction(id, info);

        // If it's not an interactive object type, skip rendering as a button
        if (clickAction == null) {
            return;
        }
        Double x = info.position().x();
        Double y = info.position().y();

        ImageView imageView = makeButton(info.imgPath(), x, y, clickAction);

        renderedObjects.put(id, imageView);
        gamePane.getChildren().add(imageView);
    }


    private void clear() {
        gamePane.getChildren().clear();
        renderedObjects.clear();
    }

    private ImageView makeButton(String resourcePath,
                                 double x, double y, Runnable onClick) {
        ImageView button = new ImageView(imgLoader.loadImage(resourcePath));

        button.setLayoutX(x);
        button.setLayoutY(y);

        button.setPickOnBounds(true);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(e -> onClick.run());

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        return button;
    }

}