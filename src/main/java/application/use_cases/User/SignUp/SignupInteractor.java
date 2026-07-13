package application.use_cases.User.SignUp;

import domain.entities.User.CommonUserFactory;
import domain.entities.User.User;

/**
 * The Signup interactor.
 */
public class SignupInteractor implements SignUpInputBoundary{
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final CommonUserFactory commonUserFactory;

    public SignupInteractor (SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            CommonUserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.commonUserFactory = userFactory;
    }

    @Override
    public void executeSignup(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatedPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            final User user = commonUserFactory.createCommonUser(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
