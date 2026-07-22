package interface_adapter.User.Signup;

import application.use_cases.User.SignUp.SignupOutputBoundary;
import application.use_cases.User.SignUp.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private Runnable switchToLoginCallback = () -> { };

    public SignupPresenter(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
    }

    public void setSwitchToLoginCallback(Runnable callback) {
        this.switchToLoginCallback = callback;
    }

    @Override
    public void prepareFailView(String errorMessage) {
        signupViewModel.getState().setErrorMessage(errorMessage);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        signupViewModel.getState().setErrorMessage("");
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        switchToLoginCallback.run();
    }
}