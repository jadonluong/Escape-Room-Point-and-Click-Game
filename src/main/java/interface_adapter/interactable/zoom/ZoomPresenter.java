package interface_adapter.interactable.zoom;

import application.use_cases.interactable.zoom.ZoomOutputBoundary;
import application.use_cases.interactable.zoom.ZoomOutputData;
import interface_adapter.ViewManagerModel;

public class ZoomPresenter implements ZoomOutputBoundary {
    private final ZoomViewModel zoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public ZoomPresenter(final ZoomViewModel zoomViewModel, final ViewManagerModel viewManagerModel) {
        this.zoomViewModel = zoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareZoomInView(ZoomOutputData outputData) {
        ZoomState state = zoomViewModel.getState();
        state.setName(outputData.getName());
        state.setDescription(outputData.getDescription());
        state.setSprite(outputData.getSprite());
        state.setInteractLabel(outputData.getInteractLabel());
        state.setInteractableId(outputData.getInteractableId());
        state.setPuzzleId(outputData.getPuzzleId());

        zoomViewModel.setState(state);
        zoomViewModel.firePropertyChanged();

        viewManagerModel.setState("Zoom");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareZoomOutView() {
        viewManagerModel.setState("in-game");
        viewManagerModel.firePropertyChanged();
    }
}
