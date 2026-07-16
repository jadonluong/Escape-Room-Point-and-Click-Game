package application.use_cases.User.Login;

import domain.entities.User.CommonUserFactory;
import domain.entities.User.User;

/**
 * The Login interactor.
 */
public class LoginInteractor implements LoginInputBoundary{
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary userPresenter;
    private final CommonUserFactory commonUserFactory;

    public LoginInteractor(LoginUserDataAccessInterface loginUserDataAccessInterface,
                           LoginOutputBoundary loginPresenter,
                           CommonUserFactory userFactory) {
        this.userDataAccessObject = loginUserDataAccessInterface;
        this.userPresenter = loginPresenter;
        this.commonUserFactory = userFactory;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!userDataAccessObject.existByName(username)) {
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
            }

            User commonUser = commonUserFactory.restoreCommonUser(username, password,
                    userDataAccessObject.getUser(username).getItemInventory(),
                    userDataAccessObject.getUser(username).getRoomsUnlocked());

            final LoginOutputData outputData = new LoginOutputData(commonUser, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
