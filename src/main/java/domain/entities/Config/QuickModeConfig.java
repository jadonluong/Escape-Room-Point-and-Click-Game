package domain.entities.Config;

public class QuickModeConfig implements GameModeConfig {
    public String StartingRoom;

    public QuickModeConfig(String StartingRoom) {
        this.StartingRoom = StartingRoom;
    }
    @Override
    public void configure() {
        //TODO: Setup this after the premade rooms are done.
    }

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }
}