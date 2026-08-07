package interface_adapter.Hint;

import application.use_cases.Hint.GetHint.GetHintOutputBoundary;
import application.use_cases.Hint.GetHint.GetHintOutputData;
import interface_adapter.ViewManagerInterface;

public class GetHintPresenter implements GetHintOutputBoundary {
    private final GetHintViewModel getHintViewModel;
    private final ViewManagerInterface viewManager;

    public GetHintPresenter(GetHintViewModel getHintViewModel, ViewManagerInterface viewManager) {
        this.getHintViewModel = getHintViewModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(GetHintOutputData getHintOutputData) {
        final String message = getHintOutputData.getMessage();
        final GetHintState state = getHintViewModel.getState();
        state.setSuccessMessage(message);
        getHintViewModel.setState(state);
        getHintViewModel.firePropertyChanged();

        viewManager.showOverlay("get hint");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        getHintViewModel.getState().setErrorMessage(errorMessage);
        getHintViewModel.firePropertyChanged();
    }
}
