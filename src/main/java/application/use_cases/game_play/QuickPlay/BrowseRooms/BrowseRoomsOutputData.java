package application.use_cases.game_play.QuickPlay.BrowseRooms;

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
