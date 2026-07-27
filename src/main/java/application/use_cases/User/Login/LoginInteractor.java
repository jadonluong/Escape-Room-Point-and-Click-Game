package application.use_cases.User.Login;

import domain.entities.User.CommonUser;

/**
 * The Login interactor.
 */

// TODO: take commonUserFactory out of app builder
public class LoginInteractor implements LoginInputBoundary{
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary userPresenter;

    public LoginInteractor(LoginUserDataAccessInterface loginUserDataAccessInterface,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = loginUserDataAccessInterface;
        this.userPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("User does not exist");
        }

        else if (username.isEmpty()) {
            userPresenter.prepareFailView("Username cannot be empty.");
        }

        else if (password.isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty.");
        }

        else {
            String pwdRegistered = userDataAccessObject.getUserPassword(username).getPassword();
            if (!pwdRegistered.equals(password)) {
                userPresenter.prepareFailView("Password incorrect");
                return;
            }

            CommonUser loadedUser = userDataAccessObject.getUser(username);

            final LoginOutputData outputData = new LoginOutputData(loadedUser, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
