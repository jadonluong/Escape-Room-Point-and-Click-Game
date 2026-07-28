package domain.entities.Config;


import java.util.ArrayList;
import java.util.List;

public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using roomId.
    String StartingRoom = "";

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}
