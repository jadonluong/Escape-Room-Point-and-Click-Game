package interface_adapter.puzzle.enter_exit;

import interface_adapter.ViewModel;

public class EnterExitViewModel extends ViewModel<EnterExitState> {
    public EnterExitViewModel() {
        super("Puzzle");
        setState(new EnterExitState());
    }
}
