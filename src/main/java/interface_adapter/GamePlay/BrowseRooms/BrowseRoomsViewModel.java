package interface_adapter.GamePlay.BrowseRooms;

import interface_adapter.ViewModel;

public class BrowseRoomsViewModel extends ViewModel<BrowseRoomsState> {

    public BrowseRoomsViewModel() {
        super("browse rooms");
        setState(new BrowseRoomsState());
    }
}
