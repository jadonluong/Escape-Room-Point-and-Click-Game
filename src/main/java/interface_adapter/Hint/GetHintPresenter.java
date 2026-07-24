package interface_adapter.Hint;

import application.use_cases.Hint.GetHint.GetHintOutputBoundary;
import application.use_cases.Hint.GetHint.GetHintOutputData;

public class GetHintPresenter implements GetHintOutputBoundary {
    private GetHintViewModel getHintViewModel;

    public GetHintPresenter(GetHintViewModel getHintViewModel) {
        this.getHintViewModel = getHintViewModel;
    }

    @Override
    public void prepareSuccessView(GetHintOutputData getHintOutputData) {
        // TODO: hint appears in an overlay form at the top of the screen
    }

    @Override
    public void prepareFailView(String errorMessage) {
        getHintViewModel.getState().setErrorMessage(errorMessage);
        getHintViewModel.firePropertyChanged();
    }
}
