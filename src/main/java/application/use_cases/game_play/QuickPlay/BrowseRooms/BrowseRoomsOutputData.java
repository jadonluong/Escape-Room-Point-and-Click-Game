package application.use_cases.game_play.QuickPlay.BrowseRooms;

import java.util.Map;

public class BrowseRoomsOutputData {

    private final Map<String, RoomInfo> info;

    public BrowseRoomsOutputData(Map<String, RoomInfo> info) {

        this.info = info;
    }

    public Map<String, RoomInfo> getInfo() {

        return info;
    }
}
