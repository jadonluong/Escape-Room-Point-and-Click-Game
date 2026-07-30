package application.use_cases.User.SaveProgress;

import domain.entities.User.CommonUser;
import domain.entities.User.User;

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
        if (!saveProgressInputData.getUsername().equals(saveProgressUserDataAccessObject.getCurrentUser().getUsername())) {
            saveProgressPresenter.prepareFailView("Invalid username provided.");
        }

        User currentUser = saveProgressUserDataAccessObject.getCurrentUser();

        if (!currentUser.isRegistered()) {
            saveProgressPresenter.prepareFailView("User is in Guest Mode, progress cannot be saved.");
        }

        // Safety net
        if (!(currentUser instanceof CommonUser commonUser)) {
            saveProgressPresenter.prepareFailView("Invalid user type for saving progress.");
            return;
        }

        saveProgressUserDataAccessObject.saveProgress(commonUser);

        final SaveProgressOutputData saveProgressOutputData = new SaveProgressOutputData(
                commonUser.getUsername(),
                false);

        saveProgressPresenter.prepareSuccessView(saveProgressOutputData);
    }
}
