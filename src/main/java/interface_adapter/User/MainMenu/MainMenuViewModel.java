package interface_adapter.User.MainMenu;

import interface_adapter.ViewModel;

public class MainMenuViewModel extends ViewModel<MainMenuState> {
    public MainMenuViewModel() {
        super("main menu"); // this exact string is what ViewManager/ViewManagerModel key on
        setState(new MainMenuState());
    }
}
