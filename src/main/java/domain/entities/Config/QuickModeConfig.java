package domain.entities.Config;

import java.util.List;

public class QuickModeConfig implements GameModeConfig {

    public String StartingRoom;

    public QuickModeConfig(String StartingRoom) {
        this.StartingRoom = StartingRoom;
    }


    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}