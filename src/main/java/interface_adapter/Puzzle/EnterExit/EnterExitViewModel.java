package interface_adapter.Puzzle.EnterExit;

import interface_adapter.ViewModel;

public class EnterExitViewModel extends ViewModel<EnterExitState> {
    public EnterExitViewModel() {
        super("Puzzle");
        setState(new EnterExitState());
    }
}
