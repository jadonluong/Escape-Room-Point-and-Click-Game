package interface_adapter.GamePlay.TutorialAndStoryModeStartUp;

import interface_adapter.ViewModel;

public class TutorialAndStoryModeStartUpViewModel extends ViewModel<TutorialAndStoryModeStartUpState>{
    public TutorialAndStoryModeStartUpViewModel() {
        super("tutorial and story mode starting");
        setState(new TutorialAndStoryModeStartUpState());
    }
}
