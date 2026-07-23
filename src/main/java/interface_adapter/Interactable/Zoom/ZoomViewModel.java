package interface_adapter.Interactable.Zoom;

import interface_adapter.ViewModel;

public class ZoomViewModel extends ViewModel<ZoomState> {
    public ZoomViewModel() {
        super("Zoom");
        setState(new ZoomState());
    }
}
