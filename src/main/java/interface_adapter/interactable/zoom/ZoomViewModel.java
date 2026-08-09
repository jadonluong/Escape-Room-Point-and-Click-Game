package interface_adapter.interactable.zoom;

import interface_adapter.ViewModel;

public class ZoomViewModel extends ViewModel<ZoomState> {
    public ZoomViewModel() {
        super("Zoom");
        setState(new ZoomState());
    }
}
