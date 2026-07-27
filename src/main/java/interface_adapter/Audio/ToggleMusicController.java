package interface_adapter.Audio;

import application.use_cases.Audio.ToggleMusic.ToggleMusicInputBoundary;

public class ToggleMusicController {
    private final ToggleMusicInputBoundary interactor;

    public ToggleMusicController(ToggleMusicInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void toggleMusic() {
        interactor.toggleMusic();
    }
}
