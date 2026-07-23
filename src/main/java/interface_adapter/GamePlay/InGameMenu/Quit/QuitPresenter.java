package interface_adapter.GamePlay.InGameMenu.Quit;

import application.use_cases.GamePlay.Quit.QuitOutputBoundary;
import interface_adapter.GamePlay.InGameMenu.InGameMenuViewModel;
import interface_adapter.ViewManagerModel;

public class QuitPresenter implements QuitOutputBoundary{

    private MainMenuViewModel mainMenuViewModel;
    private ViewManagerModel viewManagerModel;
    public InGameMenuViewModel inGameMenuViewModel;

public QuitPresenter(MainMenuViewModel mainMenuViewModel,
                     ViewManagerModel viewManagerModel,
                     InGameMenuViewModel inGameMenuViewModel) {
    this.mainMenuViewModel = mainMenuViewModel;
    this.viewManagerModel = viewManagerModel;
    this.inGameMenuViewModel = inGameMenuViewModel;
}

@Override
public void prepareMenuView() {
// 1. Clear any previous error on the menu view model
    this.inGameMenuViewModel.setErrorMessage(null);

    // 2. Switch screen back to Main Menu via ViewManagerModel
    this.viewManagerModel.setState(this.mainMenuViewModel.getViewName());
    this.viewManagerModel.firePropertyChanged();
}

@Override
public void prepareFailView(String message) {
    // Display the failure error message directly on the in-game menu
    this.inGameMenuViewModel.setErrorMessage(message);
    this.inGameMenuViewModel.firePropertyChanged();
}
}