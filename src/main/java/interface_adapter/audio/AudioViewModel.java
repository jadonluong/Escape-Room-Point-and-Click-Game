package interface_adapter.audio;

import interface_adapter.ViewModel;

/**
 * View model that stores and provides the current audio settings.
 *
 * <p>The audio state tracks whether music and sound effects are enabled.</p>
 */
public class AudioViewModel extends ViewModel<AudioState> {

    /**
     * Creates an audio view model with the default audio settings.
     */
    public AudioViewModel() {
        super("audio");
        setState(new AudioState());
    }
}
