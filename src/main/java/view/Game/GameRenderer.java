package view.Game;

import application.use_cases.GamePlay.ObjectsInfo;
import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.InGameState;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GameRenderer {

    private final Pane gamePane;
    private final Map<String, ImageView> renderedObjects = new HashMap<>();

    private final ImgLoader imgLoader = new ImgLoader();

    private final ActionTriggerController actionTriggerController;

    public GameRenderer(Pane objectPane, ActionTriggerController actionTriggerController) {
        this.gamePane = objectPane;
        this.actionTriggerController = actionTriggerController;
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

        ImageView imageView = makeButton(info.imgPath(), 50, 50,
                 () -> actionTriggerController.execute(id,info.type()));

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