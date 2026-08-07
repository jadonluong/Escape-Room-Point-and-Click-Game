package interface_adapter.GamePlay;

import java.util.HashMap;
import java.util.Map;

import application.use_cases.game_play.ObjectsInfo;

public class InGameState {
    private Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();
    private String errorMessage;
    private String imgPath;

    public InGameState() {

    }

    public Map<String, ObjectsInfo> getObjectsToDisplay() {

        return objectsToDisplay;
    }

    public String getImgPath() {

        return imgPath;
    }

    public void setObjectsToDisplay(Map<String, ObjectsInfo> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
