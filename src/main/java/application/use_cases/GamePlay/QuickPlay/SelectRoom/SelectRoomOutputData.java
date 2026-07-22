package application.use_cases.GamePlay.QuickPlay.SelectRoom;

import java.util.List;

public class SelectRoomOutputData {
    private final String roomId;

    public SelectRoomOutputData(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() { return roomId; }
}