package interface_adapter.user.save_progress;

import interface_adapter.ViewModel;

public class SaveProgressViewModel extends ViewModel<SaveProgressState> {

    public SaveProgressViewModel() {
        super("save progress");
        setState(new SaveProgressState());
    }
}
