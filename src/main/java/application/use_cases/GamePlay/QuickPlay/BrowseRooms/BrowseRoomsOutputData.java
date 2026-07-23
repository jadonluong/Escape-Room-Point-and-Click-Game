package application.use_cases.GamePlay.QuickPlay.BrowseRooms;

import java.util.List;

public class BrowseRoomsOutputData {

    public List<String> Ids;

    public BrowseRoomsOutputData(List<String> Ids) {
        this.Ids = Ids;
    }

    public List<String> getIds() {
        return Ids;
    }
}
