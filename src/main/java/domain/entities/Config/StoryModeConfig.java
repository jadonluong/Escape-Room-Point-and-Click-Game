package domain.entities.Config;


import java.util.ArrayList;
import java.util.List;

public class StoryModeConfig implements GameModeConfig {

    //Set the starting room using room Id.
    String StartingRoom = "";
    List<String> interactableObjects = new ArrayList<>();
    //TODO: Set the starting room after the rooms are implemented.

    @Override
    public void configure() {
        //TODO: Setup this after the premade rooms are done.
    }

    @Override
    public String getStartingRoomId() {
        return StartingRoom;
    }

    @Override
    public List<String> getInteractableObjects() {
        return interactableObjects;
    }
}
