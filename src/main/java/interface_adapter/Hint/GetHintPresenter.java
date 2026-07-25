package interface_adapter.Hint;

import application.use_cases.Hint.GetHint.GetHintOutputBoundary;
import application.use_cases.Hint.GetHint.GetHintOutputData;

public class GetHintPresenter implements GetHintOutputBoundary {
    private final GetHintViewModel getHintViewModel;

    public GetHintPresenter(GetHintViewModel getHintViewModel) {
        this.getHintViewModel = getHintViewModel;
    }

    @Override
    public void prepareSuccessView(GetHintOutputData getHintOutputData) {
        String message = getHintOutputData.getMessage();
        GetHintState state = getHintViewModel.getState();
        state.setSuccessMessage(message);
        getHintViewModel.setState(state);
        getHintViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        getHintViewModel.getState().setErrorMessage(errorMessage);
        getHintViewModel.firePropertyChanged();
    }
}
