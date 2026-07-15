package application.use_cases.User.SaveProgress;

/**
 * The interactor for the Save Progress Use Case.
 */
public class SaveProgressInteractor implements SaveProgressInputBoundary{
    private final SaveProgressOutputBoundary saveProgressPresenter;
    private final SaveProgressUserDataAccessInterface saveProgressUserDataAccessObject;

    public SaveProgressInteractor(SaveProgressOutputBoundary saveProgressOutputBoundary,
                                  SaveProgressUserDataAccessInterface saveProgressUserDataAccessInterface) {
        this.saveProgressPresenter = saveProgressOutputBoundary;
        this.saveProgressUserDataAccessObject = saveProgressUserDataAccessInterface;
    }

    @Override
    public void execute(SaveProgressInputData saveProgressInputData) {
        if (!saveProgressInputData.getRegisteredStatus()) {
            saveProgressPresenter.prepareFailView("User is in Guest Mode, progress cannot be saved.");
        }
        else {
            saveProgressUserDataAccessObject.saveProgress(saveProgressInputData.getUsername(),
                    saveProgressInputData.getRoomsUnlocked(),
                    saveProgressInputData.getItemInventory());
            final SaveProgressOutputData saveProgressOutputData = new SaveProgressOutputData(
                    saveProgressInputData.getUsername(),
                    false);
            saveProgressPresenter.prepareSuccessView(saveProgressOutputData);
        }
    }
}
