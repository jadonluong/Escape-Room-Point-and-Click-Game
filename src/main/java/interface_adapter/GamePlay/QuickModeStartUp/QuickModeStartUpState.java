package interface_adapter.GamePlay.QuickModeStartUp;

import java.util.HashMap;
import java.util.Map;

public class QuickModeStartUpState {
    private Map<String, String> objectsToDisplay = new HashMap<>();
    private String errorMessage = null;


    public QuickModeStartUpState() {}

    public Map<String, String> getObjectsToDisplay() {
        return objectsToDisplay;
    }

    public void setObjectsToDisplay(Map<String, String> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

