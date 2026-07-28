package interface_adapter.GamePlay.TutorialAndStoryModeStartUp;

import interface_adapter.GamePlay.InGameState;
import interface_adapter.ViewModel;

public class TutorialAndStoryModeStartUpViewModel extends ViewModel<InGameState>{
    public TutorialAndStoryModeStartUpViewModel() {
        super("tutorial and story mode starting");
        setState(new InGameState());
    }
}
