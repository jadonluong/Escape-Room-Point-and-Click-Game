package interface_adapter.GamePlay.Quit;

import application.use_cases.GamePlay.Quit.QuitOutputBoundary;
import interface_adapter.ViewManagerModel;
import interface_adapter.MainMenu.MainMenuViewModel;

public class QuitPresenter implements QuitOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;

    public QuitPresenter(ViewManagerModel viewManagerModel, MainMenuViewModel mainMenuViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
    }

    @Override
    public void prepareMenuView() {
        // 1. Switch the active view to the main menu
        viewManagerModel.setActiveView(mainMenuViewModel.getViewName());

        // 2. Alert the ViewManager
        viewManagerModel.firePropertyChanged();
    }
}