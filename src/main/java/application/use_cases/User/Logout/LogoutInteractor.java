package application.use_cases.User.Logout;

import domain.entities.User.GuestUser;

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

        userDataAccessObject.setCurrentUser(new GuestUser()); // was: setCurrentUser(null)
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username, false);

        logoutPresenter.prepareUnsavedSuccessView(logoutOutputData);
    }
}
