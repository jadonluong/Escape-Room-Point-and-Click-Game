package domain.entities.config;


public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using roomId.
    String StartingRoom = "";

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

}
