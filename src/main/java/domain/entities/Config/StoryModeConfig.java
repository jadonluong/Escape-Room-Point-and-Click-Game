package domain.entities.Config;


public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using room Id.
    String StartingRoom = "";
    //TODO: Set the starting room after the rooms are implemented.

    @Override
    public void configure() {
        //TODO: Setup this after the premade rooms are done.
    }

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }
}
