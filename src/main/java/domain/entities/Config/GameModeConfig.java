package domain.entities.Config;

import java.util.List;

public interface GameModeConfig {
    void configure();
    String getStartingRoomId();
    List<String> getInteractableObjects();
}
