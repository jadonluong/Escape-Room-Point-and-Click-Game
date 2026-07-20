package domain.entities.Config;

import java.util.List;

public class QuickModeConfig implements GameModeConfig {

    public String StartingRoom;
    public List<String> interactableObjects;

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

    @Override
    public List<String> getInteractableObjects() {
        return interactableObjects;
    }
}