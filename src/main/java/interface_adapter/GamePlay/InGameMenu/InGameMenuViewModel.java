package interface_adapter.GamePlay.InGameMenu;

import interface_adapter.ViewModel;

public class InGameMenuViewModel extends ViewModel {
    private String errorMessage;


    public InGameMenuViewModel() {
        super("in-game menu");
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
