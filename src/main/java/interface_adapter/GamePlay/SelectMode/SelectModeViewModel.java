package interface_adapter.GamePlay.SelectMode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SelectModeViewModel {
    private String activeScreen = "MENU_SCREEN"; // e.g., MENU_SCREEN, GAME_SCREEN
    private String errorMessage = "";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getActiveScreen() { return activeScreen; }
    public void setActiveScreen(String screenName) {
        String oldScreen = this.activeScreen;
        this.activeScreen = screenName;
        support.firePropertyChange("activeScreen", oldScreen, screenName);
    }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) {
        String oldError = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldError, errorMessage);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
