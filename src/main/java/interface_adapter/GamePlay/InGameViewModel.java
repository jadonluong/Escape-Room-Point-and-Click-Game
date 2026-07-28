package interface_adapter.GamePlay;

import interface_adapter.ViewModel;

public class InGameViewModel extends ViewModel<InGameState> {
    public InGameViewModel() {
        super("in-game");
        setState(new InGameState());
    }
}
