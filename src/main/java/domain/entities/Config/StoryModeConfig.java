package domain.entities.Config;


import java.util.ArrayList;
import java.util.List;

public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using room Id.
    String StartingRoom = "";

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}
