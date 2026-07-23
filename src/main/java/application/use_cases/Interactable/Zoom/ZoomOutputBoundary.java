package application.use_cases.Interactable.Zoom;

import application.use_cases.Interactable.Interact.InteractOutputData;

public interface ZoomOutputBoundary {
    void prepareZoomInView(ZoomOutputData outputData);
    void prepareZoomOutView();
}
