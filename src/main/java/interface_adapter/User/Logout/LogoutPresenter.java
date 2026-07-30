package interface_adapter.User.Logout;

import application.use_cases.User.Logout.LogoutOutputBoundary;
import application.use_cases.User.Logout.LogoutOutputData;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutOutputBoundary;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutOutputData;
import interface_adapter.User.LoggedIn.LoggedInState;
import interface_adapter.User.LoggedIn.LoggedInViewModel;
import interface_adapter.User.MainMenu.MainMenuState;
import interface_adapter.User.MainMenu.MainMenuViewModel;
import interface_adapter.User.SaveProgress.SaveProgressViewModel;
import interface_adapter.ViewManagerModel;

public class LogoutPresenter implements LogoutOutputBoundary, SaveAndLogoutOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final SaveProgressViewModel saveProgressViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           MainMenuViewModel mainMenuViewModel,
                           LoggedInViewModel loggedInViewModel,
                           SaveProgressViewModel saveProgressViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.saveProgressViewModel = saveProgressViewModel;
    }

    @Override
    public void prepareUnsavedSuccessView(LogoutOutputData logoutOutputData) {
        clearLoggedInState();

        changeMainMenuState("Logged out, progress not saved!");

        switchToMainMenu();

    }

    @Override
    public void prepareSavedSuccessView(SaveAndLogoutOutputData outputData) {
        clearLoggedInState();

        changeMainMenuState("Logged out and progress saved!");

        switchToMainMenu();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        saveProgressViewModel.getState().setErrorMessage(errorMessage);
        saveProgressViewModel.firePropertyChanged();
    }


    private void clearLoggedInState() {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername("");
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }

    private void changeMainMenuState(String message) {
        MainMenuState mainMenuState = mainMenuViewModel.getState();
        mainMenuState.setStatusMessage(message);
        mainMenuViewModel.setState(mainMenuState);
        mainMenuViewModel.firePropertyChanged();
    }

    private void switchToMainMenu() {
        this.viewManagerModel.setState(mainMenuViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
