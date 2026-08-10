package interface_adapter.interactable.zoom;

import application.use_cases.interactable.zoom.ZoomOutputBoundary;
import application.use_cases.interactable.zoom.ZoomOutputData;
import interface_adapter.ViewManagerInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.interactable.interact.InteractState;
import interface_adapter.interactable.interact.InteractViewModel;

public class ZoomPresenter implements ZoomOutputBoundary {
    private final ZoomViewModel zoomViewModel;
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManagerInterface viewManager;

    public ZoomPresenter(ZoomViewModel zoomViewModel, InteractViewModel interactViewModel,
                         ViewManagerModel viewManagerModel, ViewManagerInterface viewManager) {
        this.zoomViewModel = zoomViewModel;
        this.interactViewModel = interactViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareZoomInView(ZoomOutputData outputData) {
        final ZoomState state = zoomViewModel.getState();
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

    @Override
    public void prepareFailureView(String errorMessage) {
        final InteractState state = interactViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);

        interactViewModel.setState(state);
        interactViewModel.firePropertyChanged();

        viewManager.showOverlay("Interact");
    }
}
