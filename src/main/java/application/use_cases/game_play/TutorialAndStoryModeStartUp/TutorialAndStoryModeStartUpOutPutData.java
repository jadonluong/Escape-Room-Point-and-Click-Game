package application.use_cases.game_play.TutorialAndStoryModeStartUp;

import application.use_cases.game_play.ObjectsInfo;

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
