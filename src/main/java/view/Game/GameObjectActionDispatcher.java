package view.Game;

import application.use_cases.game_play.ObjectsInfo;
import interface_adapter.Hint.GetHintController;
import interface_adapter.Interactable.Zoom.ZoomController;
import interface_adapter.item.PickUpController;

public class GameObjectActionDispatcher {

    private final ZoomController zoomInController;
    private final GetHintController getHintController;
    private final PickUpController pickUpController;

    public GameObjectActionDispatcher(ZoomController zoomInController,
                                      GetHintController getHintController,
                                      PickUpController pickUpController) {
        this.zoomInController = zoomInController;
        this.getHintController = getHintController;
        this.pickUpController = pickUpController;
    }

    /**
     * Executes the appropriate controller action based on the object's type.
     * @return true if an action was handled, false if the type is unrecognized/non-interactive.
     */
    public Runnable getAction(String id, ObjectsInfo info) {
        String type = info.type();

        if ("Interactable".equals(type)) {
            return () -> zoomInController.zoomIn(id);
        } else if ("Item".equals(type)) {
            return () -> pickUpController.execute(id);
        } else if ("Hint".equals(type)) {
            return () -> getHintController.execute(id);
        }

        return null; // Non-interactive type
    }
}