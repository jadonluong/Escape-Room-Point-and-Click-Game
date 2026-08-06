package interface_adapter.GamePlay.BrowseRooms;

import application.use_cases.game_play.QuickPlay.BrowseRooms.RoomInfo;
import java.util.HashMap;
import java.util.Map;

public class BrowseRoomsState {
    private Map<String, RoomInfo> roomInfoMap = new HashMap<>();
    private String errorMessage = null;

    public BrowseRoomsState() {}

    public Map<String, RoomInfo> getRoomInfo() {
        return roomInfoMap;
    }

    public void setRoomInfo(Map<String, RoomInfo> roomInfoMap) {
        this.roomInfoMap = roomInfoMap;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}