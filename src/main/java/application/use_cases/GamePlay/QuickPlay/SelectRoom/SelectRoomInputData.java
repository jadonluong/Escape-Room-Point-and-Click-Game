package application.use_cases.GamePlay.QuickPlay.SelectRoom;

public class SelectRoomInputData {
    private final String targetRoomId;

    public SelectRoomInputData(String targetRoom) {
        this.targetRoomId = targetRoom;
    }

    public String getTargetRoom() {
        return targetRoom;
    }
}
