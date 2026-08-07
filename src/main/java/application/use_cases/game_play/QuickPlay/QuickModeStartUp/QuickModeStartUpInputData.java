package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

public class QuickModeStartUpInputData {
    private final String roomId;

    /**
     * Creates a new input data object for starting a quick mode game.
     *
     * @param roomId the ID of the room to start the game in
     */
    public QuickModeStartUpInputData(String roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the target room ID for the quick mode game.
     *
     * @return the ID of the room to start the game in
     */
    public String getTargetRoom() {
        return roomId;
    }
}
