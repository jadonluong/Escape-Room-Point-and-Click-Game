package interface_adapter.User.SaveProgress;

import interface_adapter.ViewModel;

public class SaveProgressViewModel extends ViewModel<SaveProgressState> {

    public SaveProgressViewModel() {
        super("save progress");
        setState(new SaveProgressState());
    }
}
