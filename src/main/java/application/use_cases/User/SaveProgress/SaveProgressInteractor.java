package application.use_cases.User.SaveProgress;

import domain.entities.User.CommonUser;
import domain.entities.User.User;

/**
 * The interactor for the Save Progress Use Case.
 */
public class SaveProgressInteractor implements SaveProgressInputBoundary {
    private final SaveProgressOutputBoundary saveProgressPresenter;
    private final SaveProgressUserDataAccessInterface saveProgressUserDataAccessObject;
    private final SaveProgressUserSessionDataAccessInterface saveProgressUserSessionDataAccessObject;

    public SaveProgressInteractor(SaveProgressOutputBoundary saveProgressOutputBoundary,
                                  SaveProgressUserDataAccessInterface saveProgressUserDataAccessInterface,
                                  SaveProgressUserSessionDataAccessInterface saveProgressUserSessionDataAccessObject) {
        this.saveProgressPresenter = saveProgressOutputBoundary;
        this.saveProgressUserDataAccessObject = saveProgressUserDataAccessInterface;
        this.saveProgressUserSessionDataAccessObject = saveProgressUserSessionDataAccessObject;
    }

    @Override
    public void execute(SaveProgressInputData saveProgressInputData) {
        if (!saveProgressInputData.getUsername().equals(saveProgressUserSessionDataAccessObject.getCurrentUser()
                .getUsername())) {
            saveProgressPresenter.prepareFailView("Invalid username provided.");
            return;
        }

        final User currentUser = saveProgressUserSessionDataAccessObject.getCurrentUser();

        if (!currentUser.isRegistered()) {
            saveProgressPresenter.prepareFailView("User is in Guest Mode, progress cannot be saved.");
            return;
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
