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
    private final ProfanityCheck profanityCheckInterface;

    public SignupInteractor (SignupUserDataAccessInterface signupDataAccessInterface,
                             SignupOutputBoundary signupOutputBoundary,
                             CommonUserFactory userFactory,
                             ProfanityCheck profanityCheckInterface) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.commonUserFactory = userFactory;
        this.profanityCheckInterface = profanityCheckInterface;
    }

    @Override
    public void executeSignup(SignupInputData signupInputData) {
        if (signupInputData.getUsername().isEmpty()) {
            userPresenter.prepareFailView("Username cannot be empty.");
        }

        else if (signupInputData.getPassword().isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty.");
        }

        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatedPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }

        else if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }

        else if (profanityCheckInterface.hasProfanity(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username contains inappropriate language.");
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
