package interface_adapter.interactable.zoom;

import application.use_cases.interactable.zoom.ZoomInputBoundary;
import application.use_cases.interactable.zoom.ZoomInputData;

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
