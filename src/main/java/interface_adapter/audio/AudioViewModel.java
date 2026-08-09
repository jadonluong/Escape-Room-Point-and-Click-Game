package interface_adapter.audio;

import interface_adapter.ViewModel;

public class AudioViewModel extends ViewModel<AudioState> {
    public AudioViewModel() {
        super("audio");
        setState(new AudioState());
    }
}
