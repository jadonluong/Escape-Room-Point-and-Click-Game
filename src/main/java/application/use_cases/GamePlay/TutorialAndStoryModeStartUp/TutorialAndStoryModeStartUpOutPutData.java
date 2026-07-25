package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

import java.util.Map;

public class TutorialAndStoryModeStartUpOutPutData {
    private Map<String,String> objectsToDisplay;

    public TutorialAndStoryModeStartUpOutPutData(Map<String,String> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
    }
    public Map<String,String> getObjectToDisplay() {
        return objectsToDisplay;
    }
}
