package interface_adapter.Interactable.Zoom;

import application.use_cases.Interactable.Zoom.ZoomOutputBoundary;
import application.use_cases.Interactable.Zoom.ZoomOutputData;
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
        state.setUser(outputData.getUser());
        state.setInteractableId(outputData.getInteractableId());
        state.setPuzzleId(outputData.getPuzzleId());

        viewManagerModel.setState("Zoom");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareZoomOutView() {
        viewManagerModel.setState("Room");
        viewManagerModel.firePropertyChanged();
    }
}
