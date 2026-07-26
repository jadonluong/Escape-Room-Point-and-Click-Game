package interface_adapter.GamePlay.QuickModeStartUp;

import interface_adapter.ViewModel;

public class QuickModeStartUpViewModel extends ViewModel<QuickModeStartUpState> {
    public QuickModeStartUpViewModel() {
        super("quick mode starting");
        setState(new QuickModeStartUpState());
    }
}
