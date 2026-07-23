package interface_adapter.GamePlay.QuickPlay.BrowseRooms;

import java.util.ArrayList;
import java.util.List;

public class BrowseRoomsState {

    private List<String> roomIds = new ArrayList<>();
    private String error = null;

    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
