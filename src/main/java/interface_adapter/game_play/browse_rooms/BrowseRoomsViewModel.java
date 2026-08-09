package interface_adapter.game_play.browse_rooms;

import interface_adapter.ViewModel;

public class BrowseRoomsViewModel extends ViewModel<BrowseRoomsState> {

    public BrowseRoomsViewModel() {
        super("browse rooms");
        setState(new BrowseRoomsState());
    }
}
