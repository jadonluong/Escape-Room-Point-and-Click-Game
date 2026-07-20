package application.use_cases.GamePlay.QuickPlay.SelectRoom;

import java.util.List;

public class SelectRoomOutputData {
    private final String roomId;
    private final List<String> interactableObjects;
    private final boolean isLocked;

    public SelectRoomOutputData(String roomId, List<String> interactableObjects, boolean isLocked) {
        this.roomId = roomId;
        this.interactableObjects = interactableObjects;
        this.isLocked = isLocked;
    }

    public String getRoomId() { return roomId; }
    public List<String> getInteractableObjects() { return interactableObjects; }
    public boolean isLocked() { return isLocked; }
}