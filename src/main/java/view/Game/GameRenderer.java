package view.Game;

import application.use_cases.game_play.ObjectsInfo;
import interface_adapter.GamePlay.ActionTrigger.ActionTriggerController;
import interface_adapter.GamePlay.InGameState;
import interface_adapter.Hint.GetHintController;
import interface_adapter.Interactable.Zoom.ZoomController;
import interface_adapter.item.PickUpController;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class GameRenderer {

    private final Pane gamePane;
    private final Map<String, ImageView> renderedObjects = new HashMap<>();

    private final ImgLoader imgLoader = new ImgLoader();

    private final ZoomController zoomInController;
    private final GetHintController getHintController;
    private final PickUpController pickUpController;

    public GameRenderer(Pane objectPane, ZoomController zoomInController, GetHintController getHintController, PickUpController pickUpController) {
        this.gamePane = objectPane;
        this.zoomInController = zoomInController;
        this.getHintController = getHintController;
        this.pickUpController = pickUpController;
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

        Double x = info.position().x();
        Double y = info.position().y();
        ImageView imageView = new ImageView();
        if (info.type().equals("Interactable")) {
            imageView = makeButton(info.imgPath(), x, y,
                    () -> zoomInController.zoomIn(id));
        }

        if (info.type().equals("Item")) {
            imageView = makeButton(info.imgPath(), x, y,
                    () -> pickUpController.execute(id));
        }

        if (info.type().equals("Hint")) {
            imageView = makeButton(info.imgPath(), x, y,
                    () -> getHintController.execute(id));
        }

        else{
            return;
        }


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