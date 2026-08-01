package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;

import java.util.Map;

public class QuickModeStartUpOutputData {
    private final Map<String, ObjectsInfo> roomInfo;
    private String roomImgPath;

    public QuickModeStartUpOutputData(Map<String,ObjectsInfo> roomInfo, String roomImgPath) {
        this.roomInfo = roomInfo;
        this.roomImgPath = roomImgPath;
    }

    public Map<String,ObjectsInfo> getObjectToDisplay() { return roomInfo; }

    public String getRoomImgPath(){ return roomImgPath; }
}