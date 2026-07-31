package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;

import java.util.Map;

public class TutorialAndStoryModeStartUpOutPutData {
    private Map<String, ObjectsInfo> objectsToDisplay;
    private String roomImgPath;

    public TutorialAndStoryModeStartUpOutPutData(Map<String,ObjectsInfo> objectsToDisplay,
                                                 String roomImgPath) {
        this.objectsToDisplay = objectsToDisplay;
        this.roomImgPath = roomImgPath;
    }
    public Map<String,ObjectsInfo> getObjectToDisplay() {
        return objectsToDisplay;
    }

    public String getRoomImgPath(){ return roomImgPath; }
}
