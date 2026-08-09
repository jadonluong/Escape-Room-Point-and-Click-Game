package application.use_cases.user.login;

import domain.entities.user.CommonUser;

/**
 * The Login interactor.
 */

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginUserSessionDataAccessInterface userSessionDataAccessObject;
    private final LoginOutputBoundary userPresenter;

    public LoginInteractor(LoginUserDataAccessInterface loginUserDataAccessInterface,
                           LoginUserSessionDataAccessInterface userSessionDataAccessObject,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = loginUserDataAccessInterface;
        this.userSessionDataAccessObject = userSessionDataAccessObject;
        this.userPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (username == null || username.isBlank()) {
            userPresenter.prepareFailView("Username cannot be empty.");
        }

        else if (!userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("user does not exist");
        }

        else if (password == null || password.isBlank()) {
            userPresenter.prepareFailView("Password cannot be empty.");
        }

        else {
            final String pwdRegistered = userDataAccessObject.getUserPassword(username).getPassword();
            if (!pwdRegistered.equals(password)) {
                userPresenter.prepareFailView("Password incorrect");
            }

            else {
                final CommonUser loadedUser = userDataAccessObject.getUser(username);
                userSessionDataAccessObject.setCurrentUser(loadedUser);

                final LoginOutputData outputData = new LoginOutputData(loadedUser, false);
                userPresenter.prepareSuccessView(outputData);
            }
        }
    }
}
