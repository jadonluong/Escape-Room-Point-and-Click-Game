package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

public class QuickModeStartUpInputData {
    private final String roomId;

    public QuickModeStartUpInputData(String roomId) {
        this.roomId = roomId;
    }

    public String getTargetRoom() {
        return roomId;
    }
}
