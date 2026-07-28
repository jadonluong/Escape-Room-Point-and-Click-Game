package interface_adapter.Interactable.Zoom;

import application.use_cases.Interactable.Zoom.ZoomInputBoundary;
import application.use_cases.Interactable.Zoom.ZoomInputData;
import domain.entities.User.User;

public class ZoomController {
    private final ZoomInputBoundary inputBoundary;

    public ZoomController(ZoomInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void zoomIn(User user, String interactableId) {
        final ZoomInputData inputData = new ZoomInputData(user, interactableId);
        inputBoundary.zoomIn(inputData);
    }

    public void zoomOut() {
        inputBoundary.zoomOut();
    }
}
