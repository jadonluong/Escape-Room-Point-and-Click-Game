package application.use_cases.GamePlay.QuickPlay.SelectRoom;

public class SelectRoomInputData {
    private final String targetRoom;

    public SelectRoomInputData(String targetRoom) {
        this.targetRoom = targetRoom;
    }

    public String getTargetRoom() {
        return targetRoom;
    }
}
