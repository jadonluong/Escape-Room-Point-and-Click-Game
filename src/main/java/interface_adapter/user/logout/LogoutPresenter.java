package interface_adapter.user.logout;

import application.use_cases.user.logout.LogoutOutputBoundary;
import application.use_cases.user.logout.LogoutOutputData;
import application.use_cases.user.save_and_logout.SaveAndLogoutOutputBoundary;
import application.use_cases.user.save_and_logout.SaveAndLogoutOutputData;
import interface_adapter.user.logged_in.LoggedInState;
import interface_adapter.user.logged_in.LoggedInViewModel;
import interface_adapter.user.main_menu.MainMenuState;
import interface_adapter.user.main_menu.MainMenuViewModel;
import interface_adapter.user.save_progress.SaveProgressViewModel;
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
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername("");
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }

    private void changeMainMenuState(String message) {
        final MainMenuState mainMenuState = mainMenuViewModel.getState();
        mainMenuState.setStatusMessage(message);
        mainMenuViewModel.setState(mainMenuState);
        mainMenuViewModel.firePropertyChanged();
    }

    private void switchToMainMenu() {
        this.viewManagerModel.setState(mainMenuViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
