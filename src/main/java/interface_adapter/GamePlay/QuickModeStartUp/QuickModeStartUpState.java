package interface_adapter.GamePlay.QuickModeStartUp;

import application.use_cases.game_play.ObjectsInfo;

import java.util.HashMap;
import java.util.Map;

public class QuickModeStartUpState {
    private Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();
    private String errorMessage = null;


    public QuickModeStartUpState() {}

    public Map<String, ObjectsInfo> getObjectsToDisplay() {
        return objectsToDisplay;
    }

    public void setObjectsToDisplay(Map<String, ObjectsInfo> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

