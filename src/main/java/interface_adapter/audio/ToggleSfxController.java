package interface_adapter.audio;

import application.use_cases.audio.toggle_sfx.ToggleSfxInputBoundary;

public class ToggleSfxController {
    private final ToggleSfxInputBoundary interactor;

    public ToggleSfxController(ToggleSfxInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void toggleSfx() {
        interactor.toggleSfx();
    }
}
