package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;

import java.util.Map;

public class QuickModeStartUpOutputData {
    private final Map<String, ObjectsInfo> roomInfo;

    public QuickModeStartUpOutputData(Map<String,ObjectsInfo> roomInfo) {
        this.roomInfo = roomInfo;
    }

    public Map<String,ObjectsInfo> getObjectToDisplay() { return roomInfo; }
}