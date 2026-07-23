package interface_adapter.User.SaveProgress;

import application.use_cases.User.SaveProgress.SaveProgressOutputBoundary;
import application.use_cases.User.SaveProgress.SaveProgressOutputData;

public class SaveProgressPresenter implements SaveProgressOutputBoundary {
    private SaveProgressViewModel saveProgressViewModel;

    public SaveProgressPresenter(SaveProgressViewModel saveProgressViewModel) {
        this.saveProgressViewModel = saveProgressViewModel;
    }

    @Override
    public void prepareSuccessView(SaveProgressOutputData saveProgressOutputData) {
        boolean isSaveFailed = saveProgressOutputData.isSaveFailed();
        if (!isSaveFailed) {
            SaveProgressState saveProgressState = saveProgressViewModel.getState();
            saveProgressState.setSuccessMessage("Progress saved!");
            saveProgressViewModel.setState(saveProgressState);
            saveProgressViewModel.firePropertyChanged();
        }
        // TODO: how is success view presented
    }

    @Override
    public void prepareFailView(String errorMessage) {
        saveProgressViewModel.getState().setErrorMessage(errorMessage);
        saveProgressViewModel.firePropertyChanged();
    }
}
