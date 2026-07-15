package application.use_cases.User.Logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary{
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        final String username = logoutInputData.getUsername();
        final boolean toSave = logoutInputData.getSaveProgress();

        userDataAccessObject.setCurrentUsername(null);
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username, false);

        if (!toSave) {
            logoutPresenter.prepareUnsavedSuccessView(logoutOutputData);
        }
        else {
            logoutPresenter.prepareSavedSuccessView(logoutOutputData);
        }
    }
}
