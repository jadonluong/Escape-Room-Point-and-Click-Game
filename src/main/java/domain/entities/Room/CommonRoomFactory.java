package domain.entities.Room;

import domain.entities.Interactable.Interactable;

public class CommonRoomFactory implements RoomFactory {

    @Override
    public Room createRoom(String roomId, String description, String path, Boolean isLocked) {
        CommonRoom Room = new CommonRoom("Id","description","path",true);
        //TODO: Add the object inside this room.
        Interactable Thing = null;
        Room.addInteractable(Thing);

        return Room;
    }
}