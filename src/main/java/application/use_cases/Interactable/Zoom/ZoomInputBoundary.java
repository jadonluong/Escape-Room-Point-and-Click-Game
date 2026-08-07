package application.use_cases.Interactable.Zoom;

/**
 * Input boundary for the Zoom use case.
 */
public interface ZoomInputBoundary {

    /**
     * Zooms in on the specified interactable.
     *
     * @param inputData the input data containing the interactable to zoom in on
     */
    void zoomIn(ZoomInputData inputData);

    /**
     * Zooms out of the currently zoomed interactable.
     */
    void zoomOut();
}
