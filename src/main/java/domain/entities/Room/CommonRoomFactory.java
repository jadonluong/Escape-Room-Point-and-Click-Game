package domain.entities.Room;

import domain.entities.Interactable.Interactable;

import java.util.List;

public class CommonRoomFactory implements RoomFactory {

    @Override
    public Room createRoom(String roomId, String description, String path, List<String> Interactable) {
        CommonRoom Room = new CommonRoom("Id","description","path", Interactable);

        return Room;
    }
}