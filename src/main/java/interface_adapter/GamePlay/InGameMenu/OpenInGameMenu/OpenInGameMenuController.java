package interface_adapter.GamePlay.InGameMenu.OpenInGameMenu;

import interface_adapter.GamePlay.InGameMenu.InGameMenuViewModel;
import interface_adapter.ViewManagerModel;

public class OpenInGameMenuController {

    private final InGameMenuViewModel inGameMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public OpenInGameMenuController(InGameMenuViewModel inGameMenuViewModel,
                                    ViewManagerModel viewManagerModel) {
        this.inGameMenuViewModel = inGameMenuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Switches the active view/overlay to the Pause Menu.
     */
    public void execute() {
        // Switch view to In-game Menu
        this.viewManagerModel.setState(this.inGameMenuViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
