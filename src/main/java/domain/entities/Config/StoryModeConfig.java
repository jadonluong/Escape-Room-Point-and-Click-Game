package domain.entities.Config;


import java.util.ArrayList;
import java.util.List;

public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using room Id.
    String StartingRoom = "";
    //TODO: Set the starting room after the rooms are implemented.

    //TODO: Setup this after the premade rooms are done.

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}
