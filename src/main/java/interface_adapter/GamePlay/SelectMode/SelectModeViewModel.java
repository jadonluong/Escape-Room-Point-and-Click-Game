package interface_adapter.GamePlay.SelectMode;

import interface_adapter.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsState;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SelectModeViewModel extends ViewModel<SelectModeState>{
    public SelectModeViewModel() {
        super("select mode");
        setState(new SelectModeState());
    }
}
