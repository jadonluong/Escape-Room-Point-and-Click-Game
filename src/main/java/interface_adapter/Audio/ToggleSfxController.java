package interface_adapter.Audio;

import application.use_cases.Audio.ToggleSfx.ToggleSfxInputBoundary;

public class ToggleSfxController {
    private final ToggleSfxInputBoundary interactor;

    public ToggleSfxController(ToggleSfxInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void toggleSfx() {
        interactor.toggleSfx();
    }
}
