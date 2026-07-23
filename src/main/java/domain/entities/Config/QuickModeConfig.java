package domain.entities.Config;

import java.util.List;

public class QuickModeConfig implements GameModeConfig {

    public String StartingRoom;

    public QuickModeConfig(String StartingRoom) {
        this.StartingRoom = StartingRoom;
    }
        //TODO: Setup this after the premade rooms are done.

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}