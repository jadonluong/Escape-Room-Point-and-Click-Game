package view.game;

import application.use_cases.game_play.ObjectsInfo;
import interface_adapter.hint.GetHintController;
import interface_adapter.interactable.zoom.ZoomController;
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
     * Retrieves the action associated with a game object based on its type.
     *
     * @param id the ID of the object
     * @param info the information of the object used to determine its type
     * @return a runnable action for the specified object, or {@code null} if the object is not interactive
     */
    public Runnable getAction(String id, ObjectsInfo info) {
        final String type = info.type();
        Runnable action = null;

        if ("Interactable".equals(type)) {
            action = () -> zoomInController.zoomIn(id);
        } else if ("Item".equals(type)) {
            action = () -> pickUpController.execute(id);
        } else if ("Hint".equals(type)) {
            action = () -> getHintController.execute(id);
        }

        return action;
    }
}
