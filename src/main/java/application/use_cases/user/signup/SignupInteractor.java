package application.use_cases.user.signup;

import domain.entities.user.CommonUserFactory;
import domain.entities.user.User;

/**
 * The Signup interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final CommonUserFactory commonUserFactory;
    private final ProfanityCheck profanityCheckInterface;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
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

        System.out.println("Signup called with: " + signupInputData.getUsername());

        if (signupInputData.getUsername() == null
                || signupInputData.getUsername().isEmpty()
                || signupInputData.getUsername().isBlank()) {

            System.out.println("EMPTY USERNAME");
            userPresenter.prepareFailView("Username cannot be empty.");
        }

        else if (signupInputData.getPassword() == null
                || signupInputData.getPassword().isEmpty()
                || signupInputData.getPassword().isBlank()) {

            System.out.println("EMPTY PASSWORD");
            userPresenter.prepareFailView("Password cannot be empty.");
        }

        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatedPassword())) {
            System.out.println("PASSWORD MISMATCH");
            userPresenter.prepareFailView("Passwords don't match.");
        }

        else if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            System.out.println("USER EXISTS");
            userPresenter.prepareFailView("user already exists.");
        }

        else if (profanityCheckInterface.hasProfanity(signupInputData.getUsername())) {
            System.out.println("PROFANITY DETECTED");
            userPresenter.prepareFailView("Username contains inappropriate language.");
        }

        else {
            System.out.println("SIGNUP SUCCESS");
            final User user = commonUserFactory.createCommonUser(
                    signupInputData.getUsername(),
                    signupInputData.getPassword()
            );
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData =
                    new SignupOutputData(user.getUsername(), false);

            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
