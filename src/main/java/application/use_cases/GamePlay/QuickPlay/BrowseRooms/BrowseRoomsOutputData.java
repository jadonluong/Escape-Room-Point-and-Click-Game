package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

import java.util.List;
import java.util.Map;

public class BrowseRoomsOutputData {

    public Map<String,RoomInfo> Info;

    public BrowseRoomsOutputData(Map<String,RoomInfo> Info) {
        this.Info = Info;
    }

    public Map<String,RoomInfo> getInfo() {
        return Info;
    }
}
