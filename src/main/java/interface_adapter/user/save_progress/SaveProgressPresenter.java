package interface_adapter.user.save_progress;

import application.use_cases.user.save_progress.SaveProgressOutputBoundary;
import application.use_cases.user.save_progress.SaveProgressOutputData;

public class SaveProgressPresenter implements SaveProgressOutputBoundary {
    private SaveProgressViewModel saveProgressViewModel;

    public SaveProgressPresenter(SaveProgressViewModel saveProgressViewModel) {
        this.saveProgressViewModel = saveProgressViewModel;
    }

    // Note: success view can be pause view + success message at bottom.
    @Override
    public void prepareSuccessView(SaveProgressOutputData saveProgressOutputData) {
        final boolean isSaveFailed = saveProgressOutputData.isSaveFailed();
        if (!isSaveFailed) {
            final SaveProgressState saveProgressState = saveProgressViewModel.getState();
            saveProgressState.setSuccessMessage("Progress saved!");
            saveProgressViewModel.setState(saveProgressState);
            saveProgressViewModel.firePropertyChanged();
        }
    }

    @Override
    public void prepareFailView(String errorMessage) {
        saveProgressViewModel.getState().setErrorMessage(errorMessage);
        saveProgressViewModel.firePropertyChanged();
    }
}
