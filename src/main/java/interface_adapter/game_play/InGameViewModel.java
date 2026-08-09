package interface_adapter.game_play;

import interface_adapter.ViewModel;

public class InGameViewModel extends ViewModel<InGameState> {

    public InGameViewModel() {
        super("in-game");
        setState(new InGameState());
    }
}
