package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;

import java.util.Map;

public class TutorialAndStoryModeStartUpOutPutData {
    private Map<String, ObjectsInfo> objectsToDisplay;

    public TutorialAndStoryModeStartUpOutPutData(Map<String,ObjectsInfo> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
    }
    public Map<String,ObjectsInfo> getObjectToDisplay() {
        return objectsToDisplay;
    }
}
