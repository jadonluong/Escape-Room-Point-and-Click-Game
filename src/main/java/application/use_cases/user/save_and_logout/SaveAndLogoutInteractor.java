package application.use_cases.user.save_and_logout;

import application.use_cases.user.logout.LogoutUserDataAccessInterface;
import application.use_cases.user.save_progress.SaveProgressUserDataAccessInterface;
import domain.entities.user.CommonUser;
import domain.entities.user.User;

/**
 * The interactor for the Save and Logout use case.
 */
public class SaveAndLogoutInteractor implements SaveAndLogoutInputBoundary {
    private final SaveProgressUserDataAccessInterface saveProgressDataAccessObject;
    private final LogoutUserDataAccessInterface logoutDataAccessObject;
    private final SaveAndLogoutOutputBoundary saveAndLogoutPresenter;

    public SaveAndLogoutInteractor(SaveProgressUserDataAccessInterface saveProgressDataAccess,
                                   LogoutUserDataAccessInterface logoutDataAccess,
                                   SaveAndLogoutOutputBoundary saveAndLogoutPresenter) {
        this.saveProgressDataAccessObject = saveProgressDataAccess;
        this.logoutDataAccessObject = logoutDataAccess;
        this.saveAndLogoutPresenter = saveAndLogoutPresenter;
    }

    @Override
    public void execute(SaveAndLogoutInputData inputData) {
        // 1. Core Rule Validation
        final User currentUser = logoutDataAccessObject.getCurrentUser();
        if (!inputData.getUsername().equals(currentUser.getUsername())) {
            saveAndLogoutPresenter.prepareFailView("You are not the current user");
            // This should not happen
            return;
        }

        if (!currentUser.isRegistered()) {
            saveAndLogoutPresenter.prepareFailView("user is in Guest Mode, progress cannot be saved.");
            return;
        }

        if (!(currentUser instanceof CommonUser commonUser)) {
            saveAndLogoutPresenter.prepareFailView("Invalid user instance provided.");
            return;
        }

        // 2. Perform Save Silently (Bypasses regular save presenter)
        saveProgressDataAccessObject.saveProgress(commonUser);

        // 3. Perform Session Cleanup
        logoutDataAccessObject.setCurrentUser(null);

        // 4. Trigger the Saved Logout Success View Cleanly
        final SaveAndLogoutOutputData outputData = new SaveAndLogoutOutputData(commonUser.getUsername(), false);
        saveAndLogoutPresenter.prepareSavedSuccessView(outputData);
    }
}
