package interface_adapter.Interactable.Zoom;

import application.use_cases.Interactable.Zoom.ZoomInputBoundary;
import application.use_cases.Interactable.Zoom.ZoomInputData;

public class ZoomController {
    private final ZoomInputBoundary inputBoundary;

    public ZoomController(ZoomInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void zoomIn(String interactableId) {
        final ZoomInputData inputData = new ZoomInputData(interactableId);
        inputBoundary.zoomIn(inputData);
    }

    public void zoomOut() {
        inputBoundary.zoomOut();
    }
}
