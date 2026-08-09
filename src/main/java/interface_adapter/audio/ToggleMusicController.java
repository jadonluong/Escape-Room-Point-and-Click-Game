package interface_adapter.audio;

import application.use_cases.audio.toggle_music.ToggleMusicInputBoundary;

public class ToggleMusicController {
    private final ToggleMusicInputBoundary interactor;

    public ToggleMusicController(ToggleMusicInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void toggleMusic() {
        interactor.toggleMusic();
    }
}
