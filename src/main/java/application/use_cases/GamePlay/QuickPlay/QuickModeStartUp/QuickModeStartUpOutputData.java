package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import java.util.Map;

public class QuickModeStartUpOutputData {
    private final Map<String,String> roomInfo;

    public QuickModeStartUpOutputData(Map<String,String> roomInfo) {
        this.roomInfo = roomInfo;
    }

    public Map<String,String> getObjectToDisplay() { return roomInfo; }
}