package interface_adapter.GamePlay.SelectMode;

import interface_adapter.ViewModel;

public class SelectModeViewModel extends ViewModel<SelectModeState>{
    public SelectModeViewModel() {
        super("select mode");
        setState(new SelectModeState());
    }
}
