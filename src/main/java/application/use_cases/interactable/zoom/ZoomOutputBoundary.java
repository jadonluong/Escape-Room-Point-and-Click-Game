package application.use_cases.interactable.zoom;

/**
 * Output boundary for presenting the results of the Zoom use case.
 */
public interface ZoomOutputBoundary {

    /**
     * Prepares the view for zooming in on an interactable.
     *
     * @param outputData the data required to display the zoomed-in view
     */
    void prepareZoomInView(ZoomOutputData outputData);

    /**
     * Prepares the view for zooming out to the current room.
     */
    void prepareZoomOutView();
}